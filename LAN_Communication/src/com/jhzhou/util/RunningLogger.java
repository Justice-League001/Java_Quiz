package com.jhzhou.util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

import com.jhzhou.demo.Config;

public class RunningLogger {
	static private final Logger exceptionLogger = Logger.getLogger("");
	static private final Logger userLogger = Logger.getLogger("");
	static private final Logger baseLogger = Logger.getLogger("");

	
	public RunningLogger(OutputStream...out) throws SecurityException, IOException{
		exceptionLogger.addHandler(new FileHandler(Config.LocalLog + Config.DataTime + "base.xml"));
		userLogger.addHandler(new FileHandler(Config.LocalLog + Config.DataTime + "used.xml"));
		baseLogger.addHandler(new FileHandler(Config.LocalLog + Config.DataTime + "error.xml"));
		
		exceptionLogger.addHandler(new StreamHandler(out[0], new SimpleFormatter()));
		userLogger.addHandler(new StreamHandler(out[1], new SimpleFormatter()));
		baseLogger.addHandler(new StreamHandler(out[2], new SimpleFormatter()));
		
		exceptionLogger.setLevel(Level.WARNING);
		userLogger.setLevel(Level.INFO);
		baseLogger.setLevel(Level.CONFIG);
	}
	static public void exceptionRecord(Exception e) {
		StringWriter trace = new StringWriter();
		e.printStackTrace(new PrintWriter(trace));
		exceptionLogger.warning(e.toString());
	}
	static public void userRecord(String record) {
		userLogger.info(record);
	}
	static public void baseRecord(String record) {
		baseLogger.config(record);
	}
}
