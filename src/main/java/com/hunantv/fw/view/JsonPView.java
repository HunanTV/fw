package com.hunantv.fw.view;

import java.io.IOException;
import java.io.Writer;

import com.alibaba.fastjson.JSON;
import com.hunantv.fw.result.Result;

public class JsonPView extends AbsView {

	protected String callback = "";

	public JsonPView(Object v) {
		this("", v);
	}

	public JsonPView(String callback, Object v) {
		this.callback = callback;
		this.v = v;
	}

	@Override
	public String render() {
		StringBuilder strb = new StringBuilder();
		strb.append(callback).append("(");

		if (v instanceof Result) {
			strb.append(((Result) v).toJson());
		} else {
			strb.append(JSON.toJSONString(v));
		}
		strb.append(")");
		return strb.toString();
	}

	@Override
	public void renderTo(Writer out) throws IOException {
		out.write(this.render());
	}

}
