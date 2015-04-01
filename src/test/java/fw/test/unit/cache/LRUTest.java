package fw.test.unit.cache;

import com.hunantv.fw.cache.LRU;

import junit.framework.TestCase;

public class LRUTest extends TestCase {

	public void testBase() {
		LRU<String, String> lru = new LRU<String, String>(3);
		lru.put("1", "1");
		lru.put("2", "2");
		lru.put("3", "3");
		lru.put("4", "4");
		assertEquals(3, lru.size());
		assertFalse(lru.containsKey("1"));
		assertTrue(lru.containsKey("2"));
		assertTrue(lru.containsKey("3"));
		assertTrue(lru.containsKey("4"));
	}
	
	public void testBase2() {
		LRU<String, String> lru = new LRU<String, String>(3);
		lru.put("1", "1");
		lru.put("2", "2");
		lru.put("3", "3");
		lru.get("1");
		lru.put("4", "4");
		assertEquals(3, lru.size());
		assertTrue(lru.containsKey("1"));
		assertFalse(lru.containsKey("2"));
		assertTrue(lru.containsKey("3"));
		assertTrue(lru.containsKey("4"));
	}
}
