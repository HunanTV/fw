package com.hunantv.fw.exceptions;

public class Http404 extends Exception {
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
