package com.jhzhou.thread;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.Pipe;
import java.nio.channels.Pipe.SourceChannel;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingQueue;

import com.jhzhou.event.Switch;
import com.jhzhou.format.Info;
import com.jhzhou.util.Tool;

public class PTPInteractions implements Callable<Object>{
	private final ConcurrentMap<InetAddress, Info> IP_LIST;
	private final Queue<Info>MsgStore;
	
	private final Pipe.SourceChannel sourceChannel;
	private final SocketAddress listeningPort;
	
	private final Switch button;
	
	private boolean SWICTH = true;
		
	
	/**
	 * @param iP_LIST
	 * @param msgStore
	 * @param sourceChannel
	 * @param listeningPort
	 * @param button
	 */
	public PTPInteractions(ConcurrentMap<InetAddress, Info> iP_LIST, Queue<Info> msgStore, SourceChannel sourceChannel,
			int Port, Switch button) {
		IP_LIST = iP_LIST;
		MsgStore = msgStore;
		this.sourceChannel = sourceChannel;
		this.listeningPort = new InetSocketAddress(Port);
		this.button = button;
	}
	
	@Override
	public Object call() throws Exception {
		ByteBuffer buffer = ByteBuffer.allocate(8192);
		try(ServerSocketChannel listener = ServerSocketChannel.open();
			Pipe.SourceChannel source = sourceChannel;
			Selector selector = Selector.open();){
			
			serverConfiguration(selector, listener,source);
			
			while(SWICTH) {
				packetHandle(selector, buffer);			
			}
			
		}catch(Exception e) {
			throw e;
		}
		return null;
	}
	private final void serverConfiguration(Selector sel, SelectableChannel...items) throws IOException {
		((ServerSocketChannel)items[0]).bind(listeningPort);
		((ServerSocketChannel)items[0]).configureBlocking(false);
		((ServerSocketChannel)items[0]).register(sel, SelectionKey.OP_ACCEPT);
		items[1].register(sel, SelectionKey.OP_READ);
		
	}
	private final void packetHandle(Selector sel, ByteBuffer buffer) throws ClassNotFoundException, IOException {
		Set<SelectionKey> readyKeys = sel.keys();
		Iterator<SelectionKey> Keys = readyKeys.iterator();
		
		while(Keys.hasNext()) {
			SelectionKey Key = Keys.next();
			Keys.remove();
			
			Channel packet = Key.channel();
			
			if(packet.getClass().getSimpleName().equalsIgnoreCase("SocketChannel")) {
				packetReceive((SocketChannel) packet, buffer);
			}else if(packet.getClass().getSimpleName().equalsIgnoreCase("SourceChannelImpl")){
				packetSend((Pipe.SourceChannel) packet, buffer);
			}else {
				newConnect(sel, (ServerSocketChannel) packet);
			}			
			
		}
		
	}
	private final void packetSend(Pipe.SourceChannel source, ByteBuffer buffer) throws IOException, ClassNotFoundException {
		while((source.read(buffer)) != -1) {
			buffer.flip();
			Info packet = (Info) Tool.objAnalysis(buffer.array(), buffer.limit());
			((SocketChannel) IP_LIST.get(packet).Att).write(buffer);
			buffer.clear();			
		}
	}	 
	private final void packetReceive(SocketChannel source, ByteBuffer buffer) throws IOException, ClassNotFoundException {
		while((source.read(buffer)) != -1) {
			buffer.flip();
			Info packet = (Info) Tool.objAnalysis(buffer.array(), buffer.limit());
		}			
		
	}
	private final void newConnect(Selector sel,ServerSocketChannel listener) throws IOException {
		listener.accept().configureBlocking(false).register(sel, SelectionKey.OP_READ);	
	}
}
