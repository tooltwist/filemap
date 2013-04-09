package com.tooltwist.filemap;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import com.tooltwist.xdata.XD;
import com.tooltwist.xdata.XDException;
import com.tooltwist.xdata.XSelector;

public class FileMap {
	
	private static final Object syncObj = new Object();
	
	private static Map<String, IFileGroupAdaptor> groupMapping = null;

	/**
	 * Load a filemap from a config file.
	 * Note that if ANY error occurs with loading the configuration, the filemap is not usable.
	 * 
	 * @param filemapPath
	 * @throws FilemapException
	 */
	public FileMap(String filemapPath) throws FilemapException {
		
		// If this is the first time in, load the file map
		if (groupMapping == null) {
			synchronized (syncObj) {
				if (groupMapping == null) {
					
					// Load the mapping file
					groupMapping = new HashMap<String, IFileGroupAdaptor>();
					
					File file = new File(filemapPath);
					if ( !file.exists())
						throw new FilemapException("Unknown file " + file.getAbsolutePath());
					
					try {
						InputStream inputStream = new FileInputStream(file);
						
						XD xd = new XD(inputStream);
						for (XSelector groupConfig : xd.foreach("//group")) {
							String name = groupConfig.getString("name");
							String plugin = groupConfig.getString("plugin");

							// Instantiate the new class
							Class<?> adaptorClass;
							try {
								adaptorClass = Class.forName(plugin);
							} catch (ClassNotFoundException e) {
								String error = "Unknown filemap adaptor " + plugin;
								throw new FilemapException(error, e);
							}
							
							// Check the class is the right type
							if ( !IFileGroupAdaptor.class.isAssignableFrom(adaptorClass)) {
								String error = "Class " + plugin + " does not implement " + IFileGroupAdaptor.class.getName();
								throw new FilemapException(error);
							}
							
							IFileGroupAdaptor adaptor;
							try {
								adaptor = (IFileGroupAdaptor) adaptorClass.newInstance();
							} catch (InstantiationException e) {
								String error = "Error instantiating " + plugin;
								throw new FilemapException(error, e);
							} catch (IllegalAccessException e) {
								String error = "Error instantiating " + plugin;
								throw new FilemapException(error, e);
							}
							
							// Initialize the adaptor
							try {
								adaptor.init(groupConfig);
							} catch (FilemapException e) {
								String error = "Error loading file group " + name;
								throw new FilemapException(error, e);
							}
							
							// Add the adaptor to our lookup table
							groupMapping.put(name, adaptor);
							
						}
					} catch (IOException e) {
						throw new FilemapException("Error loading filemap", e);
					} catch (XDException e) {
						throw new FilemapException("Error parsing filemap configuration", e);
//					} catch (ClassNotFoundException e) {
//					} catch (SecurityException e) {
//						throw new FilemapException("Could not find constructor for adaptor", e);
//					} catch (NoSuchMethodException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
					}
					
				}
				
			}
		}
	}


	/**
	 * Find a file group.
	 * @param groupName
	 * @return
	 * @throws IOException
	 */
	public IFileGroupAdaptor getFileGroup(String groupName) throws IOException {

		// Find the plugin in the group table
		IFileGroupAdaptor plugin = groupMapping.get(groupName);
		if (plugin == null) {
				throw new IOException("Unknown file group: " + groupName);
		}		
		return plugin;
	}
	
	
	public  OutputStream openFileForWriting(String fileGroup, String relativePath, boolean append) throws IOException {
		IFileGroupAdaptor plugin = getFileGroup(fileGroup);
		OutputStream os = plugin.openForWriting(relativePath, append);
		return os;
	}

	public  InputStream openForReading(String fileGroup, String relativePath) throws IOException {
		IFileGroupAdaptor plugin = getFileGroup(fileGroup);
		InputStream is = plugin.openForReading(relativePath);
		return is;
	}

	public  boolean exists(String fileGroup, String relativePath) throws IOException {
		IFileGroupAdaptor plugin = getFileGroup(fileGroup);
		boolean exists = plugin.exists(relativePath);
		return exists;
	}

	public  boolean isDirectory(String fileGroup, String relativePath) throws IOException {
		IFileGroupAdaptor plugin = getFileGroup(fileGroup);
		boolean isDirectory = plugin.isDirectory(relativePath);
		return isDirectory;
	}

	public  boolean isFile(String fileGroup, String relativePath) throws IOException {
		IFileGroupAdaptor plugin = getFileGroup(fileGroup);
		boolean isFile = plugin.isFile(relativePath);
		return isFile;
	}

	public  boolean mkdir(String fileGroup, String relativePath) throws IOException {
		IFileGroupAdaptor plugin = getFileGroup(fileGroup);
		boolean created = plugin.mkdir(relativePath);
		return created;
	}

	public  boolean mkdirs(String fileGroup, String relativePath) throws IOException {
		IFileGroupAdaptor plugin = getFileGroup(fileGroup);
		boolean created = plugin.mkdirs(relativePath);
		return created;
	}

}
