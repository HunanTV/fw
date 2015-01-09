package com.hunantv.fw.exceptions;

public class Http500 extends Exception {
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
