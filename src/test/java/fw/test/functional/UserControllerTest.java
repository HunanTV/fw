package fw.test.functional;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hunantv.fw.result.RestfulResult;
import com.hunantv.fw.route.Routes;
import com.hunantv.fw.tests.FakeBrowser;
import com.hunantv.fw.view.View;

import fw.test.demo.DemoServer;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

public class UserControllerTest extends TestCase {

	FakeBrowser fb = null;

	public void setUp() {
		Routes routes = DemoServer.initRoutes();
		fb = new FakeBrowser(routes);
	}

	public void testList() {
		View view = fb.get("/user/list", new HashMap<String, Object>() {
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

	public static void main(String[] args) {
		TestRunner runner = new TestRunner();
		TestSuite suite = new TestSuite();
		suite.addTestSuite(UserControllerTest.class);
		runner.run(suite);
	}
}
