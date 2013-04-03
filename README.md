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
* com.tooltwist.filemap.S2 - accesses file on the Amazon S2 cloud.


