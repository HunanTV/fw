package com.hunantv.fw.view;

import java.io.IOException;
import java.io.Writer;

import com.alibaba.fastjson.JSON;
import com.hunantv.fw.result.Result;

public class JsonView extends AbsView {

	public JsonView(Object v) {
		this.v = v;
	}
	
	@Override
	public String render() {
		if (v instanceof Result)
			return ((Result) v).toJson();
		return JSON.toJSONString(v);
	}

	@Override
    public void renderTo(Writer out) throws IOException {
	    out.write(this.render());
    }
}
