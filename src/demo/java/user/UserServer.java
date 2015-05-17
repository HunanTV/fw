package user;

import com.hunantv.fw.Application;
import com.hunantv.fw.route2.Route;
import com.hunantv.fw.route2.Routes;

public class UserServer {

    public static Routes initRoutes() {
		Routes routes = new Routes(
			Route.get("/user/index", UserController.class, "index"),
			Route.get("/user/list", UserController.class, "list"),
			
			Route.get("/user/show", UserController.class, "show"),
			
			Route.get("/user/edit", UserController.class, "edit"),
			Route.post("/user/update", UserController.class, "update"),
			
			Route.get("/user/new", UserController.class, "_new"),
			Route.post("/user/create", UserController.class, "create"),

			Route.post("/user/delete", UserController.class, "delete")
		);
		return routes;
    }

    /**
     * 可以用，但是不推荐用，除非是用配置文件。
     * 因为字符串不利于重构
     * @return
     */
    public static Routes initRoutes2() {
		Routes routes = new Routes(
			Route.get("/user/index", "user.UserController.index"),
			Route.get("/user/list", "user.UserController.list"),
			Route.get("/user/show", "user.UserController.show"),
			Route.get("/user/edit", "user.UserController.edit"),
			Route.post("/user/update", "user.UserController.update"),
			Route.get("/user/new", "user.UserController._new"),
			Route.post("/user/create", "user.UserController.create"),
			Route.post("/user/delete", "user.UserController.delete")
		);
		return routes;
    }

	public static void main(String[] args) throws Exception {
		Application app = Application.getInstance();
		app.setRoutes(initRoutes());
		app.listener(3333);
		app.setDebug(true);
		app.start();
    }
}
