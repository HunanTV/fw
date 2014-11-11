package com.hunantv.fw.exceptions;

public class NotImplementException extends RuntimeException {
	
	public NotImplementException() {
		super();
	}

	public NotImplementException(String msg) {
		super(msg);
	}

	public NotImplementException(Exception ex) {
		super(ex);
	}
}
