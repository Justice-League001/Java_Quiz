package com.jhzhou.demo;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.Exchanger;
import java.util.concurrent.SynchronousQueue;

import com.jhzhou.util.FileMsg;
import com.jhzhou.util.Info;
import com.jhzhou.util.TransferPacket;

public class FileTransferThread<V> implements Callable<V> {
	private Info info;
	private static int NO = 0;
	private int STATE;
	private final int T_NO;
	private final Queue<Info> WarehouseOutput;
	
	public FileTransferThread(Info info, Queue<Info> WarehouseOutput){
		this.info = info;
		this.WarehouseOutput = WarehouseOutput;
		T_NO = NO++;
	}
	
	private void sendInfo(Info msg) {
		 WarehouseOutput.add(msg);
	}
	@Override
	public V call() throws Exception {
		try(Socket connection = new Socket(info.IP,info.PORT);
			InputStream fileIn = new BufferedInputStream(new FileInputStream(info.MSG.toString()));
			OutputStream fileOut = new BufferedOutputStream(connection.getOutputStream());
			BufferedReader infoRead = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			PrintWriter infoWrite = new PrintWriter(connection.getOutputStream())){
			
			if(InfoComfirm(infoRead, infoWrite)){
				SendFile(fileOut, fileIn);
			}				
				
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
    private boolean InfoComfirm(BufferedReader infoRead, PrintWriter infoWrite) throws IOException{	

    	STATE = Files.size(((Path)info.MSG)) >  2147483646 ? 1000 : 1;

    	
		InfoSend(infoWrite,((Path)info.MSG).getFileName().toString());
		InfoSend(infoWrite, Files.size(((Path)info.MSG))+"");
		
		
		String msg = InfoReceive(infoRead);
		
		info.STATE = msg.toUpperCase().equals("Y") ? true : false;

		sendInfo(new Info(info.IP, 
		          info.PORT, 
		          info.NAME, 
		          1, T_NO, info.MSG,info.STATE));      //hascode
		
		return info.STATE; 
	}
    private void SendFile(OutputStream os,InputStream is) throws IOException {	
    	int b = -1;
		long totalBytesSend = 0;
		long k=0;
		
		double timeUsed = System.currentTimeMillis(); 
		while((b = is.read())!= -1) {
			os.write(b);
			totalBytesSend++;
			if(totalBytesSend % STATE == 0) {
				k+=1000;
				sendInfo(new Info(info.IP, 
						          info.PORT, 
						          info.NAME, 
						          11, T_NO,                //hascode
						          new TransferPacket((int)k/STATE, 
					(double)(totalBytesSend)/1000+"KB   "+(System.currentTimeMillis()-timeUsed)/1000+"S"),
						          info.STATE));
				timeUsed = (System.currentTimeMillis()-timeUsed)/1000;
			}
		}	
		if(totalBytesSend > k) {
			sendInfo(new Info(info.IP, 
			          info.PORT, 
			          info.NAME, 
			          11, T_NO,                    //hascode
			          new TransferPacket((int)totalBytesSend/STATE, 
			  (double)(totalBytesSend)/1000+"KB   "+(System.currentTimeMillis()-timeUsed)/1000+"S"),
			          info.STATE));
		}
		
		
		os.flush();
		is.close();
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


