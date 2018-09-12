package com.jhzhou.demo;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.Exchanger;
import java.util.concurrent.SynchronousQueue;

import com.jhzhou.util.FileMsg;
import com.jhzhou.util.Info;

public class FileReceiveThread<V> implements Callable<V> {
	private Info info;
	private final Queue<Info> WarehouseInput;
	Map<Info, SynchronousQueue<Info>>WarehouseOutput;
	private final Queue<Info> ThreadRequest;
	private final ServerSocket connection;
	                                      
	public FileReceiveThread                                         
	(ServerSocket connection, 
	 Queue<Info> WarehouseInput, 
	 Map<Info, 
	 SynchronousQueue<Info>> WarehouseOutput, 
	 Queue<Info> ThreadRequest){
		this.connection = connection;
		this.WarehouseOutput = WarehouseOutput;
		this.WarehouseInput = WarehouseInput;
		this.ThreadRequest = ThreadRequest;
	}
	
	private void sendInfo(Info msg) {
		WarehouseInput.add(msg);
	}
	private Info receiveInfo() {
		return   WarehouseOutput.get(info).poll();
	}
	
	@Override
	public V call() throws Exception {
		try (Socket remote = connection.accept();
			 InputStream fileIn = new BufferedInputStream(remote.getInputStream());
			 PrintWriter infoWrite = new PrintWriter(remote.getOutputStream());
			 BufferedReader infoRead = new BufferedReader(
					                   new InputStreamReader(remote.getInputStream()))){
			
			ThreadRequest.add(new Info("", 0, null, 0, null, true));
			
			info = new Info(remote.getInetAddress().getHostAddress(),
					remote.getPort(),
					remote.getInetAddress().getHostName(),
    		        2, null,true);
			WarehouseOutput.put(info, new SynchronousQueue<Info>());
			
			if(InfoComfirm(infoRead, infoWrite)){
					
				double timeUsed = System.currentTimeMillis();
				long totalBytesReceive = ReceiveFile(fileIn);		
				timeUsed = (System.currentTimeMillis()-timeUsed)/1000;
					
					
		        String str ="Have_Receive:"
		                   + totalBytesReceive 
		                   + "Bytes\nTime Used:"
		                   + timeUsed 
		                   + "Sec\nAverage Speed:"
		                   + ((double)totalBytesReceive/1000.)/timeUsed 
		                   +"KB/S\n";
					  
				info.MSG = str;
				sendInfo(info);
				}
					
			}catch (IOException e) {
				e.printStackTrace();
			}finally {
				WarehouseInput.remove(info);
			}
		
		return null;
	}
    private boolean InfoComfirm(BufferedReader infoRead, PrintWriter infoWrite) 
    		throws IOException{ 	
	
		sendInfo(new Info(info.IP,  
				          info.PORT,   
				          info.NAME,  
		         0,new FileMsg(InfoReceive(infoRead),Long.parseLong(InfoReceive(infoRead))),			         
		         true));
		
		Info comfirm = receiveInfo();
		info.MSG = comfirm.MSG;
		info.STATE = comfirm.STATE;
		
		if(comfirm.STATE) {
			InfoSend(infoWrite,"Y");
			return true;
		}
		else {
			InfoSend(infoWrite,"N");
			return false;
		}
		
	}
	private long ReceiveFile(InputStream is) throws IOException {
		int b = -1;
		long totalBytesReceive = 0;
			
		OutputStream os = new BufferedOutputStream(new FileOutputStream(info.MSG.toString()));
		while((b = is.read()) != -1) {
			os.write(b);
			totalBytesReceive++;
		}
		
		os.flush();
		os.close();
		
		return totalBytesReceive; 
	}
	private void InfoSend(PrintWriter write,String info) {
		write.println(info);
		write.flush();
	}
	private String InfoReceive(BufferedReader read) throws IOException {
		String info = read.readLine();
		return info;
	}
}

		
		
		
		
		
		
		
    