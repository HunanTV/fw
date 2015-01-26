package com.hunantv.fw.exceptions;

import javax.servlet.http.HttpServletResponse;

public class Http405 extends HttpException {

	protected int code = HttpServletResponse.SC_METHOD_NOT_ALLOWED;

	public Http405() {
		super();
	}

	public Http405(String msg) {
		super(msg);
	}

	public Http405(Exception ex) {
		super(ex);
	}
}
