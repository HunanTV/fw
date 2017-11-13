package user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hunantv.fw.Controller;
import com.hunantv.fw.db.DB;
import com.hunantv.fw.db.DB.Transaction;
import com.hunantv.fw.log.FwLogger;
import com.hunantv.fw.net.FwHttpClient;
import com.hunantv.fw.view.View;

public class UserController extends Controller {

	protected FwLogger logger = new FwLogger(UserController.class);

	public View helloBaidu() throws Exception {
		return this.renderJsonOrJsonP(new HashMap<String, String>() {
			{
				put("baidu-body", FwHttpClient.get("http://www.baidu.com").body);
			}
		});
	}

	public View HelloWorld() {
		return this.renderString("Hello, World");
	}

	public View index() {
		return this.redirect("/user/list");
	}

	public View list() throws Exception {
		logger.info("Begin List");
		DB db = new DB();
		List<Map<String, Object>> records = db.query("SELECT * FROM user");
		logger.info("End List");
		return this.renderHtml("user/list.html", new HashMap() {
			{
				put("users", records);
			}
		});
	}

	public View show(int id) {
		DB db = new DB();
		Map<String, Object> record = db.get("SELECT * FROM user WHERE id = ?", id);
		return this.renderHtml("user/show.html", new HashMap() {
			{
				put("user", record);
			}
		});

	}

	public View edit(int id) {
		DB db = new DB();
		this.logger.delayInfo("id", id);
		Map<String, Object> record = db.get("SELECT * FROM user WHERE id = ?", id);
		return this.renderHtml("user/edit.html", new HashMap() {
			{
				put("user", record);
			}
		});
	}

	public View update() {
		Integer id = this.getIntegerParam("id", 0);
		String name = this.getStrParam("name", "");
		Integer age = this.getIntegerParam("age", 0);
		if (id > 0) {
			DB db = new DB();
			db.execute("UPDATE user SET name = ? , age = ? WHERE id = ?", name, age, id);
		}
		return this.redirect("/user/show/" + id);
	}

	public View _new() {
		return this.renderHtml("/user/new.html");
	}

	public View create() {
		String name = this.getStrParam("name");
		Integer age = this.getIntegerParam("age");
		DB db = new DB();
		Transaction tran = db.beginTransaction();
		try {
			db.execute("INSERT INTO user(id, name, age) VALUES (0, ?, ?)", name, age);
			tran.commit();
			return this.redirect("/user/list");
		} catch (Exception ex) {
			tran.rollback();
			throw ex;
		}
	}

	public View delete(int id) {
		DB db = new DB();
		if (id > 0) {
			db.execute("DELETE from user WHERE id = ?", id);
		}
		return this.redirect("/user/list");
	}
}
