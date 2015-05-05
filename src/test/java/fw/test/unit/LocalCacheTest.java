package fw.test.unit;

import junit.framework.TestCase;

import com.hunantv.fw.cache.LocalCache;
import com.hunantv.fw.cache.LocalCacheConf;

public class LocalCacheTest extends TestCase {

	private String tbname = "bigdata";

	public void setUp() {
		LocalCache.instance().clear(tbname);
	}

	public void tearDown() {
		LocalCache.instance().clear(tbname);
	}

	public void testInCache() {
		LocalCache lc = LocalCache.instance();
		lc.set(tbname, "key1", "123456");
		String value = (String) lc.get(tbname, "key1");
		assertEquals("123456", value);
	}

	public void testNotInCache() {
		Object obj = LocalCache.instance().get(tbname, "key1");
		assertNull(obj);
	}

	public void testNotInCacheIfExpired() {
		LocalCacheConf conf = new LocalCacheConf();
		conf.expired_seconds = 1;
		conf.eternal = false;

		LocalCache lc = LocalCache.instance();
		lc.setConf(conf);

		lc.set(tbname, "key1", "123456");
		String value = (String) lc.get(tbname, "key1");
		assertEquals("123456", value);
		try {
			Thread.sleep(2000);
			assertNull(lc.get(tbname, "key1"));
			assertEquals(0, lc.table(tbname).getSize());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
