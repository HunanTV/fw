package fw.test.unit;

import junit.framework.TestCase;

import com.hunantv.fw.cache.LocalCache;

public class LocalCacheTest extends TestCase {

	private String tbname = "bigdata";

	public void setUp() {
		LocalCache.instance().clear(tbname);
	}

	public void tearDown() {
		LocalCache.instance().clear(tbname);
	}

	public void testNotInCache() {
		Object obj = LocalCache.instance().get(tbname, "key1");
		assertNull(obj);
	}

	public void testNotInCacheIfExpired() {
		LocalCache lc = LocalCache.instance();
		LocalCache.LRU_EXPIRED_SECONDS = 1L;
		lc.set(tbname, "key1", "123456");
		String value = (String) lc.get(tbname, "key1");
		assertEquals("123456", value);
		try {
			Thread.sleep(2000);
			assertNull(lc.get(tbname, "key1"));
			assertEquals(0, lc.table(tbname).size());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
