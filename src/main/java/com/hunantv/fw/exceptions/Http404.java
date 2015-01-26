package com.hunantv.fw.exceptions;

import javax.servlet.http.HttpServletResponse;

public class Http404 extends HttpException {

	protected int code = HttpServletResponse.SC_NOT_FOUND;

	public Http404() {
		super();
	}

	public Http404(String msg) {
		super(msg);
	}

	public Http404(Exception ex) {
		super(ex);
	}
}
