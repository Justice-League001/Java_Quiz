package com.jhzhou.demo;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.Exchanger;
import java.util.concurrent.SynchronousQueue;

import com.jhzhou.util.FileMsg;
import com.jhzhou.util.Info;

public class FileTransferThread<V> implements Callable<V> {
	private Info info;
	private final Queue<Info> WarehouseOutput;
	//Map<Info, SynchronousQueue<Info>> WarehouseInput;
	private final Exchanger exchanger;
	
	public FileTransferThread(Info info, Queue<Info> WarehouseOutput){
		//Map<Info, SynchronousQueue<Info>> WarehouseInput
		this.info = info;
		this.WarehouseOutput = WarehouseOutput;
		//this.WarehouseInput = WarehouseInput;
		exchanger = new Exchanger();
	}
	
	private void sendInfo(Info msg) {
		 WarehouseOutput.add(msg);
	}
//	private Info receiveInfo() {
//		return WarehouseInput.get(info).poll();
//	}
//	
	@Override
	public V call() throws Exception {
		try(Socket connection = new Socket(info.IP,info.PORT);
			InputStream fileIn = new BufferedInputStream(new FileInputStream((String) info.MSG));
			OutputStream fileOut = new BufferedOutputStream(connection.getOutputStream());
			BufferedReader infoRead = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			PrintWriter infoWrite = new PrintWriter(connection.getOutputStream())){
			
			
			//WarehouseInput.put(info, new SynchronousQueue<Info>());
				
			if(InfoComfirm(infoRead, infoWrite)){
				
				double timeUsed = System.currentTimeMillis();
				long totalBytesSend = SendFile(fileOut, fileIn);
				timeUsed = (System.currentTimeMillis()- timeUsed) / 1000;
									
				String str ="Have_Sent:"
		                   + totalBytesSend
		                   + "Bytes\nTime Used:"
		                   + timeUsed 
		                   + "Sec\nAverage Speed:"
		                   + ((double)totalBytesSend/1000.)/timeUsed
                           +"KB/S\n";
					  
				info.MSG = str;
				sendInfo(info);										
				
			}				
				
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
    private boolean InfoComfirm(BufferedReader infoRead, PrintWriter infoWrite) throws IOException{	
    	
		InfoSend(infoWrite,(((FileMsg) info.MSG)).getFileName());
		InfoSend(infoWrite,(((FileMsg) info.MSG)).getFileSize()+"");
		
		String msg = InfoReceive(infoRead);
		
		sendInfo(new Info(info.IP, info.PORT, info.NAME, info.PID, msg, true));
		
		msg = msg.toUpperCase().equals("Y") ? "1" : "0"; 
		return info.equals("1") ? true : false; 
	}
    private long SendFile(OutputStream os,InputStream is) throws IOException {	
    	int b = -1;
		long totalBytesSend = 0;
		
		while((b = is.read()) != -1) {
			os.write(b);
			totalBytesSend++;
		}
		
		os.flush();
		is.close();
		return totalBytesSend; 
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


