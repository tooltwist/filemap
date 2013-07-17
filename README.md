filemap - Virtual file groups
=============================

Filemap provides a facility to groups files together and place them in remote locations.

When accessing a file, you specify a file group, and then a relative path. For example:

    InputStream is = FileMap.openForWriting("org.trackmotion.telemetry", "12345/data.json");

The first parameter is a string that identifies the file group, and may be any value, however using a Java-package-like
notation, based upon a domain name, is recommended to help ensure uniqueness.

The second parameter is a relative path within the file group. In most cases this will be a normal directory path,
however the exact interpretation is up to the adaptor used for the group.

Similar methods exist to mirror most of the basic funtionality of java.io.File to add, delete, and list files and directories.


#### Purpose
The main purpose of filemap is to allow the smooth transition of applications from development through to CI, Staging and
Production environemnts, where the locations and type of storage for various files is likely to change.

During development files are usually stored and accessed directly on the developer's machine.

In a production environemnt, image files might be saved to a CDN, and documents might be saved to a fully redundant,
automtically backed up file store such as Amazon S3, that is shared by multiple web servers.
Similarly, config files might be located within JAR files, on the production server's file system,
or in some cases on a remote administration server.

Filemap allows applications to migrate between these environments without code changes.

#### Adaptors
Examples of adaptors include:

* com.tooltwist.filemap.LocalFilesystem - accesses file on the local file system.
* com.tooltwist.filemap.Classpath - accesses file within the classpath of the running application (readonly).
* com.tooltwist.filemap.S3 - accesses file on the Amazon S3 cloud.


#### Configuration
The exact parameters used to define a file group depend on the type of plugin, however a simple configuration
might look like this:

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
