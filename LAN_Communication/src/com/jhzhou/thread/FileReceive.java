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

public class FileReceive implements Callable{
	private final Queue<Info> Store;
	private final Info target;
	private final Config config;
	
	WritableByteChannel Record = Channels.newChannel(System.out);
	
	/**
	 * @param store
	 * @param target
	 * @param config
	 */
	public FileReceive(Queue<Info> store, Info target, Config config) {
		Store = store;
		this.target = target;
		this.config = config;
	}

	@Override
	public Object call() throws Exception {
		try(ByteChannel targetConnect = Tool.createConnect(target, config);){

			ByteBuffer buffer = ByteBuffer.allocate(8192);
			
			receiveConfirmation(targetConnect, buffer);
			transferConfirmation(targetConnect, buffer);
			receiveFile(targetConnect, buffer);
			
		}
		// TODO 自动生成的方法存根
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
	private void receiveFile(ByteChannel tc, ByteBuffer dst) throws IOException {
		if(tc.getClass().getSimpleName().equalsIgnoreCase("SocketChannel")) {	
			for(Iterator<FileInfo> it = (Iterator<FileInfo>)target.Att; it.hasNext();) {
				FileInfo fi = it.next();
				try(FileChannel fsc = FileChannel.open(fi.savePath, StandardOpenOption.CREATE,
						StandardOpenOption.WRITE)){
					dst.put(("已接收:"+fsc.transferFrom(tc, 0, fi.fileSize)).getBytes());
					dst.flip();
					Record.write(dst);
					dst.clear();
				}
			}
		}else {
			for(Iterator<FileInfo> it = (Iterator<FileInfo>)target.Att; it.hasNext();) {
				FileInfo fi = it.next();
				try(FileChannel fsc = FileChannel.open(fi.savePath, StandardOpenOption.CREATE,
						StandardOpenOption.WRITE)){
					while(tc.read(dst) != -1) {
						dst.flip();
						reportProgress(fsc.write(dst));
						dst.clear();
					}
				}
			}
		}	
	}
	private void reportProgress(long bytes) {
		
	}
}
