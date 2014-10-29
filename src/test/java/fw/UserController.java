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

    public View get() {
	return this.renderString("get");
    }

    public View save() {
	return this.renderString("save");
    }

    public View update() {
	return this.renderString("update");
    }

    public View delete() {
	return this.renderString("delete");
    }
}
