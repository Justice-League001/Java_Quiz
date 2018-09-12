package com.jhzhou.demo;

import java.awt.EventQueue;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;

import com.jhzhou.util.Info;



public class App {
	static private final BlockingQueue<Info> OutputQueue = new LinkedBlockingQueue<Info>();
	static private final Queue<Info> InputQueue = new ConcurrentLinkedQueue<Info>();
	static private final Map<Info, SynchronousQueue<Info>> ThreadMap = 
			                          new ConcurrentHashMap<Info, SynchronousQueue<Info>>();
	static private final Queue<Info> ThreadRequest = new ConcurrentLinkedQueue<Info>();
	static private final ExecutorService pool = Executors.newCachedThreadPool();
	
	
	
	public static void main(String[] args) throws InterruptedException, IOException {
		final ServerSocket FileConnection = new ServerSocket(1000);
		final ServerSocket MsgConnection = new ServerSocket(2000);
		Info STATE = null;
		boolean button = true;
		
		GUIThread window = new GUIThread(OutputQueue, InputQueue, ThreadMap, ThreadRequest); 
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window.initialize();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		
		pool.submit(new FileReceiveThread(FileConnection, InputQueue, ThreadMap, ThreadRequest));
		pool.submit(new MsgReceiveThread(MsgConnection, InputQueue, ThreadRequest));
		pool.submit(new MsgSendThread(OutputQueue));
		Thread.sleep(1000);
		
		while(button) {
			window.packetProcesse();
			while((STATE = ThreadRequest.poll())!=null) {
				switch(STATE.PID) {
				case 0:
					pool.submit(new FileReceiveThread(FileConnection, InputQueue, ThreadMap, ThreadRequest));
					break;
				case 1:
					pool.submit(new FileTransferThread(STATE, InputQueue));
					break;
				case 2:
					pool.submit(new MsgReceiveThread(MsgConnection, InputQueue, ThreadRequest));
					break;
				case 9:
					button = false;
				default:
					;
				}
			}
			Thread.sleep(1000);
		}
	}

}
