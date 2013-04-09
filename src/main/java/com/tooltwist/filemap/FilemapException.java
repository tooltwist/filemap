package com.tooltwist.filemap;

public class FilemapException extends Exception {
	private static final long serialVersionUID = -6506749436303855959L;
	
	public FilemapException(String error) {
		super(error);
	}

	public FilemapException(String error, Exception e) {
		super(error, e);
	}

}
