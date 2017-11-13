package fw.test.functional;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hunantv.fw.Controller;
import com.hunantv.fw.Result;
import com.hunantv.fw.view.View;

public class FooController extends Controller {

	protected static Logger logger = LoggerFactory.getLogger(FooController.class);

	public View index() {
		return this.redirect("/demo/list");
	}

	public View jsonp() {
		return this.renderJsonP(new HashMap<String, Integer>() {
			{
				put("req-offset", 100);
				put("req-limit", 20);
			}
		});
	}

	public View err500() throws Exception {
		throw new Exception("test 500 error");
	}

	public View list() throws Exception {
		// test logger
		logger.info("Begin List");
		logger.info("test1", "Hello");
		logger.info("test2", "Word");
		logger.info("test3", "Love");
		logger.info("End List");
		// end test logger

		Integer offset = this.getIntegerParam("offset", 0);
		Integer limit = this.getIntegerParam("limit", 0);

		return this.renderJson(new HashMap<String, Integer>() {
			{
				put("req-offset", offset);
				put("req-limit", limit);
			}
		});

	}

	public View update() {
		int id = this.getIntegerParam("id", 10);
		String name = this.getStrParam("name");
		int age = this.getIntegerParam("age", 0);
		// DB db = new DB();
		// Transaction tran = db.beginTransaction();
		// try {
		// tran.commit();
		return this.renderJson(new HashMap<String, Object>() {

			{
				put("id", id);
				put("name", name);
				put("age", age);
			}
		});
		// } catch (Exception ex) {
		// tran.rollback();
		// return this.renderString("failed");
		// }
	}
}
