package com.hunantv.fw;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hunantv.fw.utils.StringUtil;
import com.hunantv.fw.view.HtmlView;
import com.hunantv.fw.view.JsonView;
import com.hunantv.fw.view.RedirectView;
import com.hunantv.fw.view.StringView;
import com.hunantv.fw.view.View;
import com.sun.xml.internal.ws.util.StringUtils;

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

	public View renderHtml(String htmlPath) {
		return new HtmlView(htmlPath, new HashMap<String, Object>());
	}

	public View renderHtml(String htmlPath, Map<String, Object> data) {
		return new HtmlView(htmlPath, data);
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

	public Integer getIntegerParam(String name) {
		return getIntegerParam(name, null);
	}

	public Integer getIntegerParam(String name, Integer defaultValue) {
		String value = this.request.getParameter(name);
		if (value != null) {
			try {
				return Integer.valueOf(value);
			} catch (NumberFormatException ex) {
				return defaultValue;
			}
		}
		return defaultValue;
	}

	public Long getLongParam(String name) {
		return getLongParam(name, null);
	}

	public Long getLongParam(String name, Long defaultValue) {
		String value = this.request.getParameter(name);
		if (value != null) {
			try {
				return Long.valueOf(value);
			} catch (NumberFormatException ex) {
				return defaultValue;
			}
		}
		return defaultValue;
	}

	public Float getFloatParam(String name) {
		return getFloatParam(name, null);
	}

	public Float getFloatParam(String name, Float defaultValue) {
		String value = this.request.getParameter(name);
		if (value != null) {
			try {
				return Float.valueOf(value);
			} catch (NumberFormatException ex) {
				return defaultValue;
			}
		}
		return defaultValue;
	}

	public Double getDoubleParam(String name) {
		return getDoubleParam(name, null);
	}

	public Double getDoubleParam(String name, Double defaultValue) {
		String value = this.request.getParameter(name);
		if (value != null) {
			try {
				return Double.valueOf(value);
			} catch (NumberFormatException ex) {
				return defaultValue;
			}
		}
		return defaultValue;
	}

	public String getStrParam(String name) {
		return this.getStrParam(name, null);
	}

	public String getStrParam(String name, String defaultValue) {
		String value = this.request.getParameter(name);
		if (value != null)
			return value;
		return defaultValue;
	}

	public List getListParam(String name) {
		return this.getListParam(name, null);
	}

	public List getListParam(String name, List defaultValue) {
		String value = this.request.getParameter(name);
		if (value != null) {
			return Arrays.asList(StringUtil.split(value, ","));
		}
		return defaultValue;
	}

	public String[] getArrayParam(String name) {
		return this.getArrayParam(name, null);
	}

	public String[] getArrayParam(String name, String[] defaultValue) {
		String value = this.request.getParameter(name);
		if (value != null && value.trim().length() <= 0) {
			return StringUtil.split(value, ",");
		}
		return defaultValue;
	}
}
