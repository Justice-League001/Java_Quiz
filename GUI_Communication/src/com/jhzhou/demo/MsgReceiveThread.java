package com.jhzhou.demo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.Exchanger;
import java.util.concurrent.SynchronousQueue;

import com.jhzhou.util.Info;

public class MsgReceiveThread<V> implements Callable<V> {
	private Info info;
	private final Queue<Info> WarehouseInput;
	private final Exchanger<Info> exchanger;
	private final ServerSocket connection;
	
	public MsgReceiveThread
	(ServerSocket connection,  Queue<Info> WarehouseInput) {
		this.connection = connection;
		this.WarehouseInput = WarehouseInput;
		exchanger = new Exchanger();
	}
	private void sendInfo(Info msg) {
		 WarehouseInput.add(msg);
	}
	@Override
	public V call() throws Exception {

		try(Socket remote = connection.accept();	
			BufferedReader is=new BufferedReader(new InputStreamReader(remote.getInputStream()));
			BufferedWriter os = new BufferedWriter(new OutputStreamWriter(remote.getOutputStream()))){
			  
			exchanger.exchange(new Info(null, 0, null, 2, null, true));			
		    info = new Info(remote.getInetAddress().getHostAddress(),
		    		        remote.getPort(),
		    		        remote.getInetAddress().getHostName(),
		    		        2, null,true);
		     
					
			receive(is);		
										
			
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		return null;
    }
    private void receive(BufferedReader is) throws IOException{	
		String msg;
		
		while((msg = is.readLine()) != null) {
			sendInfo(new Info(info.IP, 
		              info.PORT, 
		              info.NAME,
		              info.PID,
		              msg, true));
		}
	} 
}