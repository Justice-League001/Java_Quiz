package com.jhzhou.demo;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.Exchanger;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;

import com.jhzhou.util.FileMsg;
import com.jhzhou.util.Info;
import com.jhzhou.util.TransferPacket;

public class FileReceiveThread<V> implements Callable<V> {
	
	private int STATE;
	private Info info;
	private static int NO = 0;
	private final int T_NO;
	private final Queue<Info> WarehouseInput;
	Map<Info, LinkedBlockingQueue<Info>>WarehouseOutput;
	private final Queue<Info> ThreadRequest;
	private final ServerSocket connection;
	                                      
	public FileReceiveThread                                         
	(ServerSocket connection, 
	 Queue<Info> WarehouseInput, 
	 Map<Info, LinkedBlockingQueue<Info>> WarehouseOutput, 
	 Queue<Info> ThreadRequest){
		this.connection = connection;
		this.WarehouseOutput = WarehouseOutput;
		this.WarehouseInput = WarehouseInput;
		this.ThreadRequest = ThreadRequest;
		T_NO = NO++;
	}
	
	private void sendInfo(Info msg) {
		WarehouseInput.add(msg);
	}
	private Info receiveInfo() throws InterruptedException {
		return  WarehouseOutput.get(info).take();
	}
	
	@Override
	public V call() throws Exception {
		try (Socket remote = connection.accept();
			 InputStream fileIn = new BufferedInputStream(remote.getInputStream());
			 PrintWriter infoWrite = new PrintWriter(remote.getOutputStream());
			 BufferedReader infoRead = new BufferedReader(
					                   new InputStreamReader(remote.getInputStream()))){
			
			ThreadRequest.add(new Info("", 0, null, 0, 0, null, true));
			
			info = new Info(remote.getInetAddress().getHostAddress(),
					remote.getPort(),
					remote.getInetAddress().getHostName(),
    		        0, 0, null,true);
			WarehouseOutput.put(info, new LinkedBlockingQueue<Info>());
			
			if(InfoComfirm(infoRead, infoWrite)){	
				ReceiveFile(fileIn);		
				}
					
			}catch (IOException e) {
				e.printStackTrace();
			}finally {
				WarehouseInput.remove(info);
			}
		
		return null;
	}
    private boolean InfoComfirm(BufferedReader infoRead, PrintWriter infoWrite) throws IOException, InterruptedException{ 	
    	
    	FileMsg msg = new FileMsg(InfoReceive(infoRead), Long.parseLong(InfoReceive(infoRead)));
    	info.MSG = msg;
    	
		sendInfo(info);
		
		STATE = (msg.FileSize > 2147483646 ? 1000 : 1);
		
		Info comfirm = receiveInfo();
		info.MSG = Paths.get(comfirm.MSG.toString()+msg.FileName);
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
	private void ReceiveFile(InputStream is) throws IOException {
		int b = -1;
		long totalBytesReceive = 0;
		long k=0;
		
		OutputStream os = new BufferedOutputStream(new FileOutputStream(info.MSG.toString()));
		double timeUsed = System.currentTimeMillis(); 
		while((b = is.read()) != -1) {
			os.write(b);
			totalBytesReceive++;
			if(totalBytesReceive % STATE == 0) {
				k+=1000;
				sendInfo(new Info(info.IP, 
						          info.PORT, 
						          info.NAME, 
						          10, T_NO,      //hascode
						          new TransferPacket((int)k/STATE, 
 (double)(totalBytesReceive)/1000+"KB   "+(System.currentTimeMillis()-timeUsed)/1000+"S"),
						          info.STATE));
				timeUsed = (System.currentTimeMillis()-timeUsed)/1000;
			}
		}
		if(totalBytesReceive > k) {
			sendInfo(new Info(info.IP, 
			          info.PORT, 
			          info.NAME, 
			          10, T_NO, //hascode
			          new TransferPacket((int)totalBytesReceive/STATE, 
			      (double)(totalBytesReceive)/1000+"KB   "+(System.currentTimeMillis()-timeUsed)/1000+"S"),
			          info.STATE));
		}
		
		os.flush();
		os.close();
		
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

		
		
		
		
		
		
		
    