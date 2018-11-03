package com.jhzhou.format;

public class FileMsg {
	public final String fileName;
	public final long fileSize;

	/**
	 * @param fileName
	 * @param fileSize
	 */
	public FileMsg(String fileName, long fileSize) {
		this.fileName = fileName;
		this.fileSize = fileSize;
	}
}
