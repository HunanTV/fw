package com.hunantv.fw.result;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

public class RestfulResult {

	private Map c = new HashMap();
	private static final int OK = 0;
	private static final String CODE_KEY = "code";
	private static final String MSG_KEY = "msg";
	private static final String DATA_KEY = "data";

	public RestfulResult() {
		this(RestfulResult.OK);
	}

	public RestfulResult(int ret) {
		this(ret, null);
	}

	public RestfulResult(Object data) {
		this(RestfulResult.OK, null, data);
	}

	public RestfulResult(int ret, String msg) {
		this(ret, msg, null);
	}

	public RestfulResult(int ret, String msg, Object data) {
		this.setCode(ret);
		this.setMsg(msg);
		if (null == data) {
			this.setData(new HashMap());
		} else {
			this.setData(data);
		}
	}

	public void setCode(int ret) {
		this.c.put(CODE_KEY, ret);
	}

	public void setMsg(String msg) {
		this.c.put(MSG_KEY, msg);
	}

	public void setData(Object data) {
		this.c.put(DATA_KEY, data);
	}

	public int getCode() {
		return (Integer) this.c.get(CODE_KEY);
	}

	public String getMsg() {
		return (String) this.c.get(MSG_KEY);
	}

	public Object getData() {
		return this.c.get(DATA_KEY);
	}

	public String toJson() {
		return JSON.toJSONString(c);
	}

}
