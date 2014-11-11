package fw.test.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.hunantv.fw.Controller;
import com.hunantv.fw.db.DB;
import com.hunantv.fw.db.DB.Transaction;
import com.hunantv.fw.result.RestfulResult;
import com.hunantv.fw.utils.FwLogger;
import com.hunantv.fw.view.View;

public class DemoController extends Controller {

	protected static FwLogger logger = new FwLogger(DemoController.class);

	public View index() {
		return this.redirect("/user/list");
	}

	public View list() throws Exception {
		logger.info("Begin List");
		logger.delayInfo("test1", "Hello");
		logger.delayInfo("test2", "Word");
		logger.delayInfo("test3", "Love");
		logger.info("End List");

		Integer offset = this.getIntegerParam("offset", 0);
		Integer limit = this.getIntegerParam("limit", 0);

		RestfulResult relt = new RestfulResult(new HashMap<String, Integer>() {
			{
				put("req-offset", offset);
				put("req-limit", limit);
			}
		});
		return this.renderString(relt.toJson());
	}

	public View get(int id) {
		DB db = new DB();
		Map<String, Object> record = db.get("SELECT * FROM user WHERE id = ?", id);
		return this.renderString(JSON.toJSONString(record));
	}

	public View update(int id) {
		// String name = this.request.getParameter("name");
		// int age = Integer.valueOf(this.request.getParameter("age"));
		// DB db = new DB();
		// db.execute("UPDATE user SET name = ? , age = ? WHERE id = ?", name,
		// age, id);
		// return
		// this.renderString(String.format("updateWithIdAndName OK [id = %s, age = %s, name = %s]",
		// id, age, name));
		Map<String, Integer> m = new HashMap<String, Integer>();
		m.put("id", id);
		return this.renderJson(m);
	}

	public View save() {
		String name = this.request.getParameter("name");
		int age = Integer.valueOf(this.request.getParameter("age"));
		DB db = new DB();
		Transaction tran = db.beginTransaction();
		try {
			db.execute("INSERT INTO user(id, name, age) VALUES (?, ?)", 1, name, age);
			tran.commit();
			return this.renderString("save ok");
		} catch (Exception ex) {
			tran.rollback();
			throw ex;
			// return this.renderString("save failed");
		}
	}

	public View delete(int id) {
		DB db = new DB();
		db.execute("DELETE user WHERE id = ?", id);
		return this.renderString("delete");
	}
}
