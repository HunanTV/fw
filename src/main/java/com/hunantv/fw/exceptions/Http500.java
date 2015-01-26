package com.hunantv.fw.exceptions;

import javax.servlet.http.HttpServletResponse;

public class Http500 extends HttpException {
	
	protected int code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

	public Http500() {
		super();
	}

	public Http500(String msg) {
		super(msg);
	}

	public Http500(Exception ex) {
		super(ex);
	}
}
