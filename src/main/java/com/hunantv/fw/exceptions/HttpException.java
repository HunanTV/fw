package com.hunantv.fw.exceptions;

import javax.servlet.http.HttpServletResponse;

public class HttpException extends Exception {

    private static final long serialVersionUID = -5164191922690736211L;
    public final static int ERR_404 = HttpServletResponse.SC_NOT_FOUND;
	public final static int ERR_405 = HttpServletResponse.SC_METHOD_NOT_ALLOWED;
	public final static int ERR_500 = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

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

	public HttpException(int code, Exception ex) {
		super(ex);
		this.code = code;
	}

	public int getCode() {
		return code;
	}
	
	public static HttpException err404() {
		return new HttpException(ERR_404);
	}

	public static HttpException err405() {
		return new HttpException(ERR_405);
	}

	public static HttpException err500(Exception ex) {
		return new HttpException(ERR_500, ex);
	}
}
