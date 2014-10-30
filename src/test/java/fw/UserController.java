package fw;

import com.hunantv.fw.Controller;
import com.hunantv.fw.view.View;

public class UserController extends Controller {

	public View index() {
		return this.renderString("index");
	}

	public View list() {
		return this.renderString("list");
	}

	public View getById(int id) {
		return this.renderString("get: " + id);
	}

	public View updateWithIdAndName(int id, String name) {
		return this.renderString(String.format("updateWithIdAndName => id = %s ,name = %s", id, name));
	}

	public View save(String name) {
		return this.renderString("save: " + name);
	}

	public View delete() {
		return this.renderString("delete");
	}
}
