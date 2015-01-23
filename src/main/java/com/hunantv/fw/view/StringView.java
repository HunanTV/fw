package com.hunantv.fw.view;

import java.io.IOException;
import java.io.Writer;

public class StringView extends AbsView {

	public StringView(String v) {
		this.v = v;
	}
	
	@Override
	public String render() {
		return (String) v;
	}

	@Override
	public void renderTo(Writer out) throws IOException {
		out.write(this.render());
	}
}
