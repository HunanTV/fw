package com.hunantv.fw.view;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

public interface View {
	Logger logger = LoggerFactory.getLogger(View.class);

	public abstract String render();

	public abstract void renderTo(Writer out) throws IOException;

	public abstract Object getV();

	/**
	 * Determine if this view should rendered as byte's stream, <br/>
	 * default is false,
	 *
	 * @return
	 */
	public default boolean isStreamView() {
		return false;
	}

	/**
	 * Delegate the output to a stream or a writer according to the type of <br/>
	 * view.
	 *
	 * @param response: HttpServletResponse
	 * @throws IOException
	 */
	public default void renderTo(HttpServletResponse response) throws IOException {
		if (isStreamView()) {
			renderTo(response.getOutputStream());
		} else {
			renderTo(response.getWriter());
		}
	}

	/**
	 * This is used for rendering bytes output
	 *
	 * @return View's bytes
	 */
	public default byte[] getBytes() {
		return null;
	}

	/**
	 * Render the bytes to an output stream, as this is not an original method <br/>
	 * belongs to this interface, so we implement it as the default method, which <br/>
	 * is a new feature of JDK1.8, therefore, all the subclasses of {@link View} <br/>
	 * is not mandatory to implement this method.
	 *
	 * @param out: OutputStream
	 * @throws IOException
	 */
	public default void renderTo(OutputStream out) throws IOException {
		byte[] bytes = getBytes();

		if (bytes == null) {
			logger.warn("Ignore output as no bytes loaded from this view.");
			return;
		}

		out.write(bytes);
	}
}
