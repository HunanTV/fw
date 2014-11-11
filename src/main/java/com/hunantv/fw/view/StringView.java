package com.hunantv.fw.view;

public class StringView extends AbsView {

	public StringView(String v) {
		this.v = v;
	}

	public String render() {
		return (String) v;
	}
}
