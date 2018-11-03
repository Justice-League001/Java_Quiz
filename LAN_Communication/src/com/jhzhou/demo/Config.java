package com.jhzhou.demo;

import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Config {
	
	static public InetSocketAddress group = new InetSocketAddress("224.0.0.1", 1856);
	
	static public String localIP = null;
	
	static public int LocalPort = 1868;
	
	static public NetworkInterface interf = null;
	
	static public String LocalLog = "E:\\repo\\pro\\Java_Quiz\\LAN_Communication\\"; 
	
	static public final String DataTime = (new SimpleDateFormat("YYYY-MM-dd-HH,mm")).format(new Date());
	
	
}
