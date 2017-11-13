package com.hunantv.fw.view;

import java.io.IOException;
import java.io.Writer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.hunantv.fw.Result;

public class JsonPView extends AbsView {

	protected String callback = "";

	public JsonPView(Result v) {
		this("", v);
	}

	public JsonPView(String callback, Result v) {
		this.callback = callback;
		this.v = v;
	}

	@Override
	public String render() {
		StringBuilder strb = new StringBuilder();
		strb.append(callback).append("(");
		strb.append(((Result) v).toJson());
		strb.append(")");
		return strb.toString();
	}

	@Override
	public void renderTo(Writer out) throws IOException {
		out.write(this.render());
	}

}
