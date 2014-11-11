package com.hunantv.fw.view;

public class RedirectView extends AbsView {

	private String v;

	public RedirectView(String v) {
		this.v = v;
	}

	public String render() {
		return v;
	}
}
