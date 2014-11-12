package com.hunantv.fw.view;

public class RedirectView extends AbsView {

	public RedirectView(String v) {
		this.v = v;
	}

	public String render() {
		return (String) v;
	}
}
