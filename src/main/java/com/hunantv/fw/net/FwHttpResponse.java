package com.hunantv.fw.net;

public class FwHttpResponse {

	public int code;
	public String body;
	
	public FwHttpResponse() {
		this(-1, null);
	}

	public FwHttpResponse(int code, String body) {
		this.code = code;
		this.body = body;
	}
}
