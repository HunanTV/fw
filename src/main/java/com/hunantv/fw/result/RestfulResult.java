package com.hunantv.fw.result;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

public class RestfulResult implements Result {

	private static final int OK = 0;
	protected int code = OK;
	protected String msg = "";
	protected Object data = null;

	public RestfulResult() {
		this(OK, "", null);
	}

	public RestfulResult(int code) {
		this(code, "", null);
	}

	public RestfulResult(Object data) {
		this(OK, "", data);
	}

	public RestfulResult(int code, String msg) {
		this(code, msg, null);
	}

	public RestfulResult(int code, String msg, Object data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public int getCode() {
		return this.code;
	}

	public String getMsg() {
		return this.msg;
	}

	public Object getData() {
		return this.data;
	}

	public String toJson() {
		Map<String, Object> m = this.toMap();
		return JSON.toJSONString(m);
	}

	public Map<String, Object> toMap() {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("code", this.code);
		m.put("msg", this.msg);
		m.put("data", this.data == null ? new HashMap() : data);
		return m;
	}
}
