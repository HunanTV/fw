package com.hunantv.fw.view;

import java.io.IOException;
import java.io.Writer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.hunantv.fw.Result;

public class JsonView extends AbsView {

	public JsonView(Result v) {
		this.v = v;
	}

	@Override
	public String render() {
		return ((Result) v).toJson();
	}

	@Override
	public void renderTo(Writer out) throws IOException {
		out.write(this.render());
	}
}
