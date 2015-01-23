package com.hunantv.fw.view;

import java.io.IOException;
import java.io.Writer;

public interface View {
	public abstract String render();

	public abstract void renderTo(Writer out) throws IOException;

	public abstract Object getV();
}
