package com.tooltwist.filemap;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.tooltwist.xdata.XSelector;

public interface IFileGroupAdaptor {
	
	/**
	 * <b>WARNING: <i>Do not call this method from user programs.</i></b>
	 * <p>
	 * This method is called immediately after the adaptor is instantiated, as the configuration
	 * is read from the mapping file. The config parameter contains the segment of the config file relevant to this adaptor. For example:
	 * <pre>
	 * {@code
	 * <group>
	 *     <name>com.tooltwist.home</name>
	 *     <plugin>com.tooltwist.filemap.FileSystemAdaptor</plugin>
	 *     <directory>/tooltwist/tooltwist_osx_8.0-beta/tooltwist</directory>
	 * </group>
	 * }
	 * 
	 * 
	 * @param configDetails
	 * @throws FilemapException
	 */
	void init(XSelector config) throws FilemapException;

	OutputStream openForWriting(String relativePath, boolean append) throws IOException;

	InputStream openForReading(String relativePath) throws IOException;

	boolean exists(String relativePath) throws IOException;

	boolean isDirectory(String relativePath) throws IOException;

	boolean isFile(String relativePath) throws IOException;

	boolean mkdir(String relativePath) throws IOException;

	boolean mkdirs(String relativePath) throws IOException;

	String fileDescription(String relativePath);

	Iterable<String> files(String directoryRelativePath);

}
