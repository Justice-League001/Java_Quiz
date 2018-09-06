package com.jhzhou.util;

import java.io.BufferedWriter;
import java.net.Socket;

public class ConnectionStream {
	public final Socket connection;
	public final BufferedWriter os;
	public ConnectionStream(Socket connection, BufferedWriter os) {
		this.connection = connection;
		this.os = os;
	}
}
