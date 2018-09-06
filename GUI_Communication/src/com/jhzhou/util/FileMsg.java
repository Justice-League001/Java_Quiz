package com.jhzhou.util;

public class FileMsg {
	
	private final String FileName;
	private final long FileSize;
	
	public FileMsg(String FileName, long FileSize) {
		this.FileName = FileName;
		this.FileSize = FileSize;
	}
	public String getFileName() {
		return FileName;
	}
	public long getFileSize() {
		return FileSize;
	}
}
