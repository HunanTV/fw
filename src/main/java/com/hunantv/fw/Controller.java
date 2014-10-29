package com.hunantv.fw;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Controller {

    private HttpServletRequest request;
    private HttpServletResponse response;

    public HttpServletRequest getRequest() {
	return request;
    }

    public void setRequest(HttpServletRequest request) {
	this.request = request;
    }

    public HttpServletResponse getResponse() {
	return response;
    }

    public void setResponse(HttpServletResponse response) {
	this.response = response;
    }

    public View renderString(String str) {
	return new StringView(str);
    }
}
