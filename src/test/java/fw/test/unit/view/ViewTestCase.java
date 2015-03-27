package fw.test.unit.view;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.hunantv.fw.view.BytesView;
import com.hunantv.fw.view.StringView;

/**
 * Created by thomas on 15/3/24.
 */
public class ViewTestCase {

	private ByteArrayOutputStream outputStream = null;
	private PrintWriter writer = null;
	private HttpServletResponse response = null;

	@Before
	public void setUp() throws IOException {
		outputStream = new ByteArrayOutputStream();
		writer = new PrintWriter(outputStream);

		response = createMock(HttpServletResponse.class);
		expect(response.getWriter()).andReturn(writer);
		expect(response.getOutputStream()).andReturn(new DummyServletOutputStream(outputStream));

		replay(response);

	}

	@Test
	public void testStringView() throws IOException {
		String test = "This is for test";
		StringView view = new StringView(test);

		view.renderTo(response);

		writer.flush();

		byte[] b = outputStream.toByteArray();
		Assert.assertEquals("String view is not returned as expected", test, new String(b));
	}

	@Test
	public void testBytesViewFromBytes() throws IOException {
		byte[] bytes = new byte[] { 0, 1, 2, 3, 4, 5, 6, 7 };

		BytesView view = BytesView.fromBytes(bytes);
		view.renderTo(response);

		byte[] returnedBytes = outputStream.toByteArray();
		Assert.assertArrayEquals(bytes, returnedBytes);
	}

	@Test
	public void testBytesViewFromInputStream() throws IOException {
		byte[] bytes = new byte[] { 0, 1, 2, 3, 4, 5, 6, 7 };

		ByteArrayInputStream is = new ByteArrayInputStream(bytes);
		BytesView view = BytesView.fromInputStream(is);
		view.renderTo(response);
		byte[] returnedBytes = outputStream.toByteArray();

		Assert.assertArrayEquals(bytes, returnedBytes);
	}

	@Test
	public void testBytesViewFromByteArrayOutputStream() throws IOException {
		byte[] bytes = new byte[] { 0, 1, 2, 3, 4, 5, 6, 7 };

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		out.write(bytes);
		BytesView view = BytesView.fromByteArrayOutputStream(out);

		view.renderTo(response);
		byte[] returnedBytes = outputStream.toByteArray();

		Assert.assertArrayEquals(bytes, returnedBytes);
	}

}
