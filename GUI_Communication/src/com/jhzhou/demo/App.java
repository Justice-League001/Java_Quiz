package com.jhzhou.demo;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;

import com.jhzhou.function.GUI;
import com.jhzhou.util.Info;



public class App {
	final static BlockingQueue<Info> Outputqueue = new LinkedBlockingQueue<Info>();
	final static Queue<Info> Inputqueue = new ConcurrentLinkedQueue<Info>();
	final static Map<Info, SynchronousQueue<Info>> ThreadMap = 
			new ConcurrentHashMap<Info, SynchronousQueue<Info>>();
	private final static Exchanger<Info> exchanger = new Exchanger();;
	private final static ExecutorService pool = Executors.newCachedThreadPool();
	
	
	public static void main(String[] args) throws InterruptedException {
		final ServerSocket FileConnection = new ServerSocket(1000);
		final ServerSocket MsgConnection = new ServerSocket(2000);
		
		boolean button = true;
		
		pool.submit((Callable<T>) new GUI());
		
		
		pool.submit(new FileReceiveThread(FileConnection, Inputqueue, ThreadMap));
		pool.submit(new MsgReceiveThread(MsgConnection, Inputqueue));
		pool.submit(new MsgSendThread(Outputqueue));
		
		
		while(button) {
			Info info = exchanger.exchange(null);
			switch(info.PID) {
			case 0:
				pool.submit(new FileReceiveThread(FileConnection, Inputqueue, ThreadMap));
				break;
			case 1:
				pool.submit(new FileTransferThread(info, Inputqueue));
				break;
			case 2:
				pool.submit(new MsgReceiveThread(MsgConnection, Inputqueue));
				break;
				default:
					button = false;
			}
		}
	}

}
