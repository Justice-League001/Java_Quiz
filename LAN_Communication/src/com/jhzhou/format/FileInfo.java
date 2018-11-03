package com.jhzhou.format;

import java.nio.file.Path;

public class FileInfo {
	public final Path savePath;
	public final long fileSize;
	/**
	 * @param savePath
	 * @param fileSize
	 */
	public FileInfo(Path savePath, long fileSize) {
		this.savePath = savePath;
		this.fileSize = fileSize;
	}
	
}
