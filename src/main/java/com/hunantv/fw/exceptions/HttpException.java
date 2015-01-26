package com.hunantv.fw.exceptions;

import javax.servlet.http.HttpServletResponse;

public class HttpException extends Exception {

	public final static HttpException ERR_404 = new HttpException(HttpServletResponse.SC_NOT_FOUND);
	public final static HttpException ERR_405 = new HttpException(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
	public final static HttpException ERR_500 = new HttpException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

	protected int code;

	public HttpException() {
		super();
	}

	public HttpException(int code) {
		super();
		this.code = code;
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
