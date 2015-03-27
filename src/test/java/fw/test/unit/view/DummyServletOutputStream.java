package fw.test.unit.view;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;

/**
 * Created by thomas on 15/3/24.
 */
public class DummyServletOutputStream extends ServletOutputStream {

	private OutputStream outputStream;

	public DummyServletOutputStream(OutputStream o) {
		this.outputStream = o;
	}

	@Override
	public boolean isReady() {
		return false;
	}

	@Override
	public void setWriteListener(WriteListener writeListener) {

	}

	@Override
	public void write(byte[] bytes) throws IOException {
		outputStream.write(bytes);
	}

	@Override
	public void write(byte[] b, int len, int off) throws IOException {
		outputStream.write(b, len, off);
	}

	@Override
	public void write(int b) throws IOException {
		outputStream.write(b);
	}
}
