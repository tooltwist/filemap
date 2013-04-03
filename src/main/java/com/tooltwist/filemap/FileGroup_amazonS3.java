package com.tooltwist.filemap;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileGroup_amazonS3 implements FileGroup {

	@Override
	public OutputStream openForWriting(String relativePath, boolean append) throws IOException {
		return null;
	}

	@Override
	public InputStream openForReading(String relativePath) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean exists(String relativePath) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isDirectory(String relativePath) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isFile(String relativePath) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mkdir(String relativePath) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mkdirs(String relativePath) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}
	
	public static void main(String[] args) {
		System.out.println("Hello");
	}

}
