package fw.test.functional;

import java.util.HashMap;

import junit.framework.TestCase;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hunantv.fw.exceptions.HttpException;
import com.hunantv.fw.route.Routes;
import com.hunantv.fw.tests.FakeBrowser;
import com.hunantv.fw.view.RedirectView;
import com.hunantv.fw.view.View;

import fw.test.demo.DemoServer;

public class DemoControllerTest extends TestCase {

	FakeBrowser fb = null;

	public void setUp() {
		Routes routes = DemoServer.initRoutes();
		fb = new FakeBrowser(routes);
	}

	public void test404() {
		try {
			View view = fb.get("/demo/does-not-exists-url");
			throw new RuntimeException("the line should not be run");
		} catch (HttpException ex) {
			assertEquals(404, ex.getCode());
		}
	}

	public void test500() {
		try {
			View view = fb.get("/demo/500err");
			throw new RuntimeException("the line should not be run");
		} catch (HttpException ex) {
			assertEquals(500, ex.getCode());
		}
	}

	public void testList() throws Exception {
		View view = fb.get("/demo/list", new HashMap<String, Object>() {
			{
				put("offset", 100);
				put("limit", 10);
			}
		});
		HashMap m = JSON.parseObject(view.render(), HashMap.class);
		assertEquals(0, m.get("code"));
		assertEquals("", m.get("msg"));

		JSONObject jsonObj = (JSONObject) m.get("data");
		assertEquals(10, jsonObj.getInteger("req-limit").intValue());
		assertEquals(100, jsonObj.getInteger("req-offset").intValue());
	}

	public void testIndex() throws Exception {
		View view = fb.get("/demo/index", new HashMap<String, Object>() {
			{
				put("offset", 100);
				put("limit", 10);
			}
		});
		assertTrue(view instanceof RedirectView);
		assertEquals("/demo/list", view.getV());
	}

	public void testUpdate() throws Exception {
		View view = fb.post("/demo/update", new HashMap<String, Object>() {
			{
				put("id", 10);
				put("age", 100);
				put("name", "pengyi");
			}
		});
		HashMap m = JSON.parseObject(view.render(), HashMap.class);
		assertEquals(0, m.get("code"));
		assertEquals("", m.get("msg"));

		JSONObject jsonObj = (JSONObject) m.get("data");
		assertEquals(10, jsonObj.getInteger("id").intValue());
		assertEquals(100, jsonObj.getInteger("age").intValue());
		assertEquals("pengyi", jsonObj.getString("name"));
	}
}
