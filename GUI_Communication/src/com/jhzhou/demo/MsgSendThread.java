package com.jhzhou.demo;


import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

import com.jhzhou.util.ConnectionStream;
import com.jhzhou.util.Info;

public class MsgSendThread<V> implements Callable<V> {
	private final Map<Info, ConnectionStream> table;
	private final BlockingQueue<Info> MSG;
	
	public MsgSendThread(BlockingQueue<Info> MSG) {
		this.MSG = MSG;
		table = new HashMap<Info, ConnectionStream>();
	}
	
	private boolean checkTable(Info info) {
		return table.containsKey(info);
	}
	private void createConnectionStream(Info info) throws UnknownHostException, IOException {
		Socket sck = new Socket(info.IP, info.PORT);
		table.put(info, new ConnectionStream(sck, new BufferedWriter(
				                                   new OutputStreamWriter(sck.getOutputStream()))));
	}
	@Override
	public V call() throws Exception {
		BufferedWriter os;
		Info info;
		
		
		while((info = MSG.take()).STATE) {
			if(checkTable(info) == false)
				createConnectionStream(info);
			os = table.get(info).os;
			Send((String)info.MSG, os);	
		}
		
		Iterator<Entry<Info, ConnectionStream>> iterator = table.entrySet().iterator();
		while(iterator.hasNext()) {
			Entry<Info, ConnectionStream> entry = iterator.next();
			entry.getValue().os.close();
			iterator.remove();
		}
		
		return null;
	}
	private void Send(String info,BufferedWriter os) throws IOException  { 
		os.write(info);
		os.newLine();
		os.flush();
	}
}


