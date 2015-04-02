package fw.test.unit;

import com.hunantv.fw.cache.LRU;

import junit.framework.TestCase;

public class LRUTest extends TestCase {

	public void testBase() {
		LRU lru = new LRU(3);
		lru.put("1", "1");
		lru.put("2", "2");
		lru.put("3", "3");
		lru.put("4", "4");
		assertEquals(3, lru.size());
		assertNull(lru.get("1"));
		assertEquals("2", (String) lru.get("2"));
		assertEquals("3", (String) lru.get("3"));
		assertEquals("4", (String) lru.get("4"));
	}

	public void testBase2() {
		LRU lru = new LRU(3);
		lru.put("1", "1");
		lru.put("2", "2");
		lru.put("3", "3");
		lru.get("1");
		lru.put("4", "4");
		assertEquals(3, lru.size());
		assertEquals("1", (String) lru.get("1"));
		assertNull(lru.get("2"));
		assertEquals("3", (String) lru.get("3"));
		assertEquals("4", (String) lru.get("4"));
	}

	public void testExpired() {
		LRU lru = new LRU(3);
		lru.put("1", "1", 1L);
		assertEquals(1, lru.size());
		assertEquals("1", (String) lru.get("1"));
		try {
	        Thread.sleep(2000);
	        assertEquals(1, lru.size()); // 不会自动删除
	        assertNull(lru.get("1"));
	        assertEquals(0, lru.size()); // 获取了一次后，就会自动删除了
        } catch (InterruptedException e) {
	        e.printStackTrace();
        }
	}
}
