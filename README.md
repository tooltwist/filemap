filemap - Virtual file groups
=============================

Filemap provides a facility to groups files together and place them in remote locations.

When accessing a file, you specify a file group, and then a relative path. For example:

    InputStream is = FileMap.openForWriting("org.trackmotion.telemetry", "12345/data.json");

The first parameter is a string that identifies the file group, and may be any value, however using a Java-package-like notation, based upon a domain name, is recommended to help ensure uniqueness.

The second parameter is a relative path within the file group. In most cases this will be a normal directory path, however the exact interpretation is up to the adaptor used for the group.

Examples of adaptors include:

* com.tooltwist.filemap.LocalFilesystem - accesses file on the local file system.
* com.tooltwist.filemap.Classpath - accesses file within the classpath of the running application (readonly).
* com.tooltwist.filemap.S3 - accesses file on the Amazon S3 cloud.


The main purpose of filemap is to allow the smooth transition of applications from a development through to CI, staging and production servers,
where the locations and type of storage for various files is likely to change. For example, during development all files are usually stored
on the developer's own machine. In production however, image files might be saved to a CDN, documents might be saved to a redundant file store
shared by multiple web servers, and config files might be stored within JAR files, on the production server, or perhaps even on a remote file server.

An example config file might look like this:

<filemap>

    <group>
		<name>com.tooltwist.home</name>
		<plugin>com.tooltwist.filemap.FileSystemAdaptor</plugin>
		<directory>/tooltwist/tooltwist_osx_8.0/tooltwist</directory>
	</group>

	<group>
		<name>org.trackmotion.telemetry</name>
		<plugin>com.tooltwist.filemap.FileSystemAdaptor</plugin>
		<directory>/Users/philipcallender/misc/telemetry</directory>
	</group>
	
</filemap>


This file may be stored anywere, and in the case of ToolTwist applications this file is stored at $TOOLTWIST_HOME/conf/filemap.xml.

--
