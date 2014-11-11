package com.hunantv.fw.view;

import com.alibaba.fastjson.JSON;

public class JsonView extends AbsView {

	public JsonView(Object v) {
		this.v = v;
	}

	public String render() {
		return JSON.toJSONString(v);
	}
}
