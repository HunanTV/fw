package com.hunantv.fw.view;

import com.alibaba.fastjson.JSON;
import com.hunantv.fw.result.Result;

public class JsonView extends AbsView {

	public JsonView(Object v) {
		this.v = v;
	}

	public String render() {
		if (v instanceof Result)
			return ((Result) v).toJson();
		return JSON.toJSONString(v);
	}
}
