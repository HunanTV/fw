package com.hunantv.fw.view;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

public class RedirectView extends AbsView {

	public RedirectView(String v) {
		this.v = v;
	}

	public String render() {
		return (String) v;
	}

	@Override
	public void renderTo(Writer out) throws IOException {
		/* Ignore method. */
	}

	@Override
	public void renderTo(HttpServletResponse response) throws IOException {
		response.sendRedirect(this.render());
	}
}
