package fw.test.unit;

import java.util.List;
import java.util.Map;

import com.hunantv.fw.net.URLParser;

import junit.framework.TestCase;

public class URLParserTest extends TestCase {
	public void testBasicQueryPair() {
		String url = "http://baidu.com/?a=b&c=d";
		URLParser parser = new URLParser(url);
		Map<String, String> pair = parser.getQueryPair();
		assertEquals(2, pair.size());
		assertTrue(pair.containsKey("a"));
		assertTrue(pair.containsKey("c"));
		assertEquals("b", pair.get("a"));
		assertEquals("d", pair.get("c"));
	}
	
	public void testBaicQueries() {
		String url = "http://baidu.com/?a=b&c=d";
		URLParser parser = new URLParser(url);
		List<String> queries = parser.getQueries();
		assertEquals(2, queries.size());
		assertTrue(queries.contains("a=b"));
		assertTrue(queries.contains("c=d"));
	}
}
