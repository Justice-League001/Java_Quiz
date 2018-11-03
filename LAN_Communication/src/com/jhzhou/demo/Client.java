package com.jhzhou.demo;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.nio.channels.Pipe;
import java.nio.channels.Pipe.SourceChannel;
import java.util.EventObject;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.jhzhou.event.CreateListener;
import com.jhzhou.event.Switch;
import com.jhzhou.event.SwitchEvent;
import com.jhzhou.event.SwitchListener;
import com.jhzhou.format.Info;
import com.jhzhou.thread.MulticastInteractive;
import com.jhzhou.thread.PTPInteractions;


public class Client {


	public static void main(String[] args) {
		ExecutorService pool = Executors.newCachedThreadPool();
		ConcurrentMap <InetAddress, Info> IP_LIST = new ConcurrentHashMap<InetAddress, Info>(); 
		ConcurrentLinkedQueue<Info> MsgStore = new ConcurrentLinkedQueue<Info>();	
		Pipe multicastTransfer = null, socketTransfer = null;
		try {
			multicastTransfer = Pipe.open();
			socketTransfer = Pipe.open();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
		
		
		Switch button1 = new Switch();
		Switch button2 = new Switch();
		
		button1.addListener(new SwitchListener() {

			@Override
			public void handleEvent(SwitchEvent switchEvent) {
				// TODO 自动生成的方法存根
				
			}
			
		});
		
		button2.addListener(new SwitchListener() {

			@Override
			public void handleEvent(SwitchEvent switchEvent) {
				// TODO 自动生成的方法存根
				
			}
			
		});
		
		button2.addListener(new CreateListener() {

			@Override
			public void handleEvent(SwitchEvent switchEvent) {
				// TODO 自动生成的方法存根
				
			}
			
		});
		
		pool.submit(new MulticastInteractive(IP_LIST,
				                             MsgStore,
			                    	         multicastTransfer.source(), 
				                             Config.group, 
				                             Config.interf, 
				                             button1));
		
		pool.submit(new PTPInteractions(IP_LIST, 
				                        MsgStore, 
				                        socketTransfer.source(), 
				                        Config.LocalPort,
				                        button1));
		
		//pool.submit(GUI);
		
		
		
		while(true) {
			
		}
		
	}

}
