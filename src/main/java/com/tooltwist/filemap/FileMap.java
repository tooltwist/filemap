package com.tooltwist.filemap;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class FileMap {
	
	private static Map<String, FileGroup> groupMapping = new HashMap<String, FileGroup>();

	
	public static FileGroup getFileGroup(String fileGroup) throws IOException {
		
		// Check we have a mapping object
		FileGroup plugin = groupMapping.get(fileGroup);
		if (plugin == null) {
			synchronized (groupMapping) {
				
				
				
				// This stuff should come from a file, and look up the plugin type using Google guice
				if (fileGroup.equals("org.trackmotion.telemetry")) {
					plugin = new FileGroup_localFilesystem("/Users/philipcallender/misc/telemetry");
				}
				
				
				
				if (plugin == null)
					throw new IOException("Unknown file group: " + fileGroup);
				groupMapping.put(fileGroup, plugin);
			}
		}
		
		return plugin;
	}
	
	
	public static OutputStream openFileForWriting(String fileGroup, String relativePath, boolean append) throws IOException {
		FileGroup plugin = getFileGroup(fileGroup);
		OutputStream os = plugin.openForWriting(relativePath, append);
		return os;
	}

	public static InputStream openForReading(String fileGroup, String relativePath) throws IOException {
		FileGroup plugin = getFileGroup(fileGroup);
		InputStream is = plugin.openForReading(relativePath);
		return is;
	}

	public static boolean exists(String fileGroup, String relativePath) throws IOException {
		FileGroup plugin = getFileGroup(fileGroup);
		boolean exists = plugin.exists(relativePath);
		return exists;
	}

	public static boolean isDirectory(String fileGroup, String relativePath) throws IOException {
		FileGroup plugin = getFileGroup(fileGroup);
		boolean isDirectory = plugin.isDirectory(relativePath);
		return isDirectory;
	}

	public static boolean isFile(String fileGroup, String relativePath) throws IOException {
		FileGroup plugin = getFileGroup(fileGroup);
		boolean isFile = plugin.isFile(relativePath);
		return isFile;
	}

	public static boolean mkdir(String fileGroup, String relativePath) throws IOException {
		FileGroup plugin = getFileGroup(fileGroup);
		boolean created = plugin.mkdir(relativePath);
		return created;
	}

	public static boolean mkdirs(String fileGroup, String relativePath) throws IOException {
		FileGroup plugin = getFileGroup(fileGroup);
		boolean created = plugin.mkdirs(relativePath);
		return created;
	}

}
