package com.hunantv.fw.exceptions;

public class HttpException500 extends Exception {
	public HttpException500() {
		super();
	}

	public HttpException500(String msg) {
		super(msg);
	}

	public HttpException500(Exception ex) {
		super(ex);
	}
}
