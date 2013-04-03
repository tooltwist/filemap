package com.tooltwist.filemap;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface FileGroup {

	OutputStream openForWriting(String relativePath, boolean append) throws IOException;

	InputStream openForReading(String relativePath) throws IOException;

	boolean exists(String relativePath) throws IOException;

	boolean isDirectory(String relativePath) throws IOException;

	boolean isFile(String relativePath) throws IOException;

	boolean mkdir(String relativePath) throws IOException;

	boolean mkdirs(String relativePath) throws IOException;

}
