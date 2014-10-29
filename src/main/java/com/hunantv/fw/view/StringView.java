package com.hunantv.fw.view;

public class StringView implements View {

	private String v;

	public StringView(String v) {
		this.v = v;
	}

	public String toString() {
		return v;
	}
}
