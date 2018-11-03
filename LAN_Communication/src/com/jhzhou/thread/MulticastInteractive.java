package com.jhzhou.thread;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.StandardProtocolFamily;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.DatagramChannel;
import java.nio.channels.MulticastChannel;
import java.nio.channels.Pipe;
import java.nio.channels.Pipe.SourceChannel;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;

import com.jhzhou.event.Switch;
import com.jhzhou.format.Info;
import com.jhzhou.util.Tool;


public class MulticastInteractive implements Callable<Object>{
	
	
	private final ConcurrentMap<InetAddress, Info> IP_LIST;
	private final Queue<Info> MsgStore;
	
	private final Pipe.SourceChannel sourceChannel;
	
	private final InetSocketAddress group;
	private final NetworkInterface interf;
	
	private final Switch button;
	
	 
	private boolean SWITCH = true;
	
	
	/**
	 * @param iP_LIST
	 * @param msgStore
	 * @param sourceChannel
	 * @param group
	 * @param interf
	 * @param button
	 */
	public MulticastInteractive(ConcurrentMap<InetAddress, Info> iP_LIST, Queue<Info> msgStore,
			SourceChannel sourceChannel, InetSocketAddress group, NetworkInterface interf, Switch button) {
		IP_LIST = iP_LIST;
		MsgStore = msgStore;
		this.sourceChannel = sourceChannel;
		this.group = group;
		this.interf = interf;
		this.button = button;
	}




	@Override
	public Object call() throws Exception {
		ByteBuffer buffer = ByteBuffer.allocate(8192);
		try(MulticastChannel muticast = DatagramChannel.open(StandardProtocolFamily.INET)
				         .setOption(StandardSocketOptions.SO_REUSEADDR, true)
				         .setOption(StandardSocketOptions.IP_MULTICAST_IF, interf)
				         .setOption(StandardSocketOptions.IP_MULTICAST_LOOP, false)
				         .bind(group);
			Selector selector = Selector.open();
			Pipe.SourceChannel source = this.sourceChannel;){
			
			((DatagramChannel)muticast).configureBlocking(false);
			SelectorRegister(selector, 
					        buffer,
					        (SelectableChannel)muticast, 
					        (SelectableChannel)source);
			
			muticast.join(group.getAddress(), interf);
			
				
			
			while(SWITCH) {
				groupgramHandle(selector, (DatagramChannel) muticast);
				SendEcho((DatagramChannel) muticast, buffer);
			}
		}catch(Exception e) {
				throw e;
		}
		return null;
	}
	private void SendEcho(DatagramChannel target, ByteBuffer buffer) throws IOException {
		buffer.put(Tool.objSerialization(new Info(group, 0)));
		buffer.flip();
		target.write(buffer);
	}
	private void SelectorRegister(Selector sel, Object att,SelectableChannel...items) throws ClosedChannelException {
		for(SelectableChannel item: items) {
			item.register(sel, SelectionKey.OP_READ);
		}
	}
	private void groupgramHandle(Selector selector, DatagramChannel target) throws IOException, ClassNotFoundException {
		if(selector.select(3000) > 0) {
			Set<SelectionKey> readyKeys = selector.selectedKeys();
			Iterator<SelectionKey> iterator = readyKeys.iterator();
			while(iterator.hasNext()) {
				SelectionKey key = iterator.next();
				iterator.remove();				
				
				Channel gram = key.channel();
				/*
				 * 根据类型信息判断是群组信息还是来自本地
				 */
				if(gram.getClass().getSimpleName().equalsIgnoreCase("DatagramChannel")) {
					groupMsgHandle((DatagramChannel) gram, (ByteBuffer) key.attachment());
				}else {
					localMsgHandle((SourceChannel) gram, target, (ByteBuffer) key.attachment());
				}
				
			}
		}
	}
	private void groupMsgHandle(DatagramChannel gram, ByteBuffer buffer) throws IOException, ClassNotFoundException {
		/*
		 * 将来自群组的信息发送至仓库
		 * 并触发main的信息事件
		 */
		gram.receive(buffer);
		buffer.flip();	
	
		Info msg = (Info) Tool.objAnalysis(buffer.array(), buffer.limit()), buff;  
		
	
    		if( (buff = IP_LIST.get(msg.target.getAddress())) == null ) 
    			IP_LIST.put(msg.target.getAddress(), new Info(msg.target, 0, System.currentTimeMillis()));	
    		else
    			buff.Att = System.currentTimeMillis();
   
	    	if(msg.PID > 0) {
	    		MsgStore.add(buff);
	    		/*
	    		 * 触发事件待编辑.
	    		 */
	    	}		
	}
	private void localMsgHandle(Pipe.SourceChannel gram, DatagramChannel target, ByteBuffer buffer) throws IOException {
		/*
		 * 将读取的本地信息发送至群组
		 */
		while( (gram.read(buffer)) != -1) {
			buffer.flip();
			target.write(buffer);
			buffer.clear();
		}
		
	}
	
}
