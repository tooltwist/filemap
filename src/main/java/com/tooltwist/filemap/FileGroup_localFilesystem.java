package com.tooltwist.filemap;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileGroup_localFilesystem implements FileGroup {
	private String baseDir;

	public FileGroup_localFilesystem(String baseDir) throws IOException {
		
		// Check the directory exists
		File dir = new File(baseDir);
		if ( !dir.exists())
			throw new IOException("Unknown directory: " + baseDir);
		if ( !dir.isDirectory())
			throw new IOException("Not a directory: " + baseDir);
		
		this.baseDir = dir.getAbsolutePath();
	}
	
	private String fullPath(String relativePath) {
		if (relativePath.startsWith("/"))
			return baseDir + relativePath;
		return baseDir + "/" + relativePath;
	}

	@Override
	public boolean exists(String relativePath) throws FileNotFoundException {
		
		String path = fullPath(relativePath);
		File file = new File(path);
		boolean exists = file.exists();
		return exists;
	}

	@Override
	public boolean isDirectory(String relativePath) throws FileNotFoundException {
		
		String path = fullPath(relativePath);
		File file = new File(path);
		boolean exists = file.isDirectory();
		return exists;
	}

	@Override
	public boolean isFile(String relativePath) throws FileNotFoundException {
		
		String path = fullPath(relativePath);
		File file = new File(path);
		boolean exists = file.isFile();
		return exists;
	}

	@Override
	public boolean mkdir(String relativePath) throws FileNotFoundException {
		
		String path = fullPath(relativePath);
		File file = new File(path);
		boolean wasCreated = file.mkdir();
		return wasCreated;
	}

	@Override
	public boolean mkdirs(String relativePath) throws FileNotFoundException {
		
		String path = fullPath(relativePath);
		File file = new File(path);
		boolean wasCreated = file.mkdirs();
		return wasCreated;
	}

	@Override
	public OutputStream openForWriting(String relativePath, boolean append) throws FileNotFoundException {
		
		String path = fullPath(relativePath);
		FileOutputStream outputStream = new FileOutputStream(path, append);
		return outputStream;
	}

	@Override
	public InputStream openForReading(String relativePath) throws IOException {
		
		String path = fullPath(relativePath);
		FileInputStream inputStream = new FileInputStream(path);
		return inputStream;
	}

}
