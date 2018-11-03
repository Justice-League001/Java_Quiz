package com.jhzhou.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.StandardProtocolFamily;
import java.net.StandardSocketOptions;
import java.nio.channels.ByteChannel;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SocketChannel;

import com.jhzhou.demo.Config;
import com.jhzhou.format.Info;

public class Tool {
	static public byte[] objSerialization(Object obj) throws IOException {
		/* 
		 * 将对象序列化
		 */
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		ObjectOutputStream serialize = new ObjectOutputStream(new ByteArrayOutputStream());
		serialize.writeObject(obj);
		return buffer.toByteArray();	
	}
	static public Object objAnalysis(byte []objByte, int len) throws IOException, ClassNotFoundException {
		/*
		 * 将字节解析成对象
		 */
		ObjectInputStream analysis = new ObjectInputStream(
				                     new ByteArrayInputStream(objByte, 0, len));
		return analysis.readObject();
	}
	static public ByteChannel createConnect(Info target, Config config) throws IOException {
		ByteChannel cl;
		switch(target.PID) {
		case 2:
			cl = DatagramChannel.open(StandardProtocolFamily.INET)
		              .setOption(StandardSocketOptions.IP_MULTICAST_LOOP, false)
		              .setOption(StandardSocketOptions.SO_REUSEADDR, true)
		              .setOption(StandardSocketOptions.IP_MULTICAST_IF, config.interf)
		              .bind(config.group);
			((SelectableChannel)cl).configureBlocking(true);
			((DatagramChannel)cl).join(config.group.getAddress(), config.interf);
			return cl;
		case 3:
			cl = SocketChannel.open(target.target);
			((SocketChannel)cl).configureBlocking(true); 
			return cl;
		case 4:
			break;
		case 5:
			break;
		default:
			return null;
		}
		return null;	
	}
}
