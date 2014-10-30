package com.hunantv.fw;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hunantv.fw.view.JsonView;
import com.hunantv.fw.view.RedirectView;
import com.hunantv.fw.view.StringView;
import com.hunantv.fw.view.View;

public class Controller {

	protected HttpServletRequest request;
	protected HttpServletResponse response;

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

	public View renderJson(Object object) {
		return new JsonView(object);
	}

	public View redirect(String str) {
		return new RedirectView(str);
	}
}
