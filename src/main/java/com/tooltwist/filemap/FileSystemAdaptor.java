package com.tooltwist.filemap;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import com.tooltwist.xdata.XDException;
import com.tooltwist.xdata.XSelector;

public class FileSystemAdaptor implements IFileGroupAdaptor {
	private String baseDir;

	@Override
	public void init(XSelector config) throws FilemapException {
		
		// Check there is a directory specified
		String directory;
		try {
			directory = config.getString("directory");
		} catch (XDException e) {
			throw new FilemapException("Error parsing config");
		}
		
		// Checks it's an ansolute path
		if ( !directory.startsWith("/"))
			throw new FilemapException("Directory must start with /");
		
		// Check the directory exists
		File dir = new File(directory);
		if ( !dir.exists())
			throw new FilemapException("Unknown directory: " + directory);
		
		// Check it's a directory
		if ( !dir.isDirectory())
			throw new FilemapException("Not a directory: " + directory);
		
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

	@Override
	public String fileDescription(String relativePath) {
		String path = fullPath(relativePath);
		return "(local file: " + path + ")";
	}

	@Override
	public Iterable<String> files(String directoryRelativePath) {
		if ( !directoryRelativePath.endsWith("/"))
			directoryRelativePath += "/";
		ArrayList<String>list = new ArrayList<String>();
		String absolutePath = fullPath(directoryRelativePath);
		File dir = new File(absolutePath);
		for (File file: dir.listFiles()) {
			String name = file.getName();
			String childRelativePath = directoryRelativePath + name;
			list.add(childRelativePath);
		}
		return list;
	}

}
