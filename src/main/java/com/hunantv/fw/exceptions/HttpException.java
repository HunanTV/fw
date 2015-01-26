package com.hunantv.fw.exceptions;

public class HttpException extends Exception {
	
	protected int code;

	public HttpException() {
		super();
	}

	public HttpException(String msg) {
		super(msg);
	}

	public HttpException(Exception ex) {
		super(ex);
	}

	public int getCode() {
		return code;
	}
}
