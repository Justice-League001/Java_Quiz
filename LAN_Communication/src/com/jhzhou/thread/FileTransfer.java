package com.jhzhou.thread;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.Callable;

import com.jhzhou.demo.Config;
import com.jhzhou.format.FileInfo;
import com.jhzhou.format.Info;
import com.jhzhou.util.Tool;

public class FileTransfer implements Callable{
	private final Queue<Info> Store;
	private final Info target;
	private final Config config;
	
	WritableByteChannel Record = Channels.newChannel(System.out);
	
	/**
	 * @param store
	 * @param target
	 * @param config
	 */
	public FileTransfer(Queue<Info> store, Info target, Config config) {
		Store = store;
		this.target = target;
		this.config = config;
	}

	@Override
	public Object call() throws Exception {
		// TODO 自动生成的方法存根
		try(ByteChannel targetConnect = Tool.createConnect(target, config)){
			ByteBuffer buffer = ByteBuffer.allocate(8192);
			
			transferConfirmation(targetConnect, buffer);
			receiveConfirmation(targetConnect, buffer);
			transferFile(targetConnect, buffer);
			
		}catch(Exception e) {
			throw e;
		}
		return null;
	}
	
	
	private void transferConfirmation(ByteChannel tc, ByteBuffer dst) throws IOException {
		dst.put("准备传输确认".getBytes());
		dst.flip();
		tc.write(dst);
		dst.flip();
		Record.write(dst);
		dst.clear();
	}
	private void receiveConfirmation(ByteChannel tc, ByteBuffer dst) throws IOException {
		while(tc.read(dst) != -1) {
			dst.flip();
			Record.write(dst);
			dst.clear();
		}
	}
	private void transferFile(ByteChannel tc, ByteBuffer dst) throws IOException {	
		if(tc.getClass().getSimpleName().equalsIgnoreCase("SocketChannel")) {
     		for(Iterator<FileInfo> it = (Iterator<FileInfo>)target.Att; it.hasNext();) {
    			try(FileChannel source = FileChannel.open(it.next().savePath, StandardOpenOption.READ)){
    				dst.put(("已发送:"+source.transferTo(0, source.size(), tc)).getBytes());
    	     		dst.flip();
    	     		Record.write(dst);
    	     		dst.clear();
    			}catch(IOException e){
    				throw e;
    			}
    		}	
    	}else {
	    	for(Iterator<FileInfo> it = (Iterator<FileInfo>)target.Att; it.hasNext();) {
    			try(FileChannel source = FileChannel.open(it.next().savePath, StandardOpenOption.READ)){
    				while(source.read(dst)!= -1) {
    		    		dst.flip();
    					reportProgress(tc.write(dst));
    					dst.clear();
    				}
    			}catch(IOException e){
    				throw e;
    				}
    		}
		}	
	}
	private void reportProgress(long bytes) {
		
	}

}
