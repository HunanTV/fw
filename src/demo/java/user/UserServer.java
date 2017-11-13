package user;

import com.hunantv.fw.Application;
import com.hunantv.fw.route.Route;
import com.hunantv.fw.route.Routes;

public class UserServer {

    public static Routes initRoutes() {
		Routes routes = new Routes(
			Route.get("/user/hw", UserController.class, "HelloWorld"),
			Route.get("/user/hb", UserController.class, "helloBaidu"),
			Route.get("/user/index", UserController.class, "index"),
			Route.get("/user/list", UserController.class, "list"),
			Route.get("/user/show/<int:id>", UserController.class, "show"),
			Route.get("/user/edit/<int:id>", UserController.class, "edit"),
			Route.post("/user/update", UserController.class, "update"),
			Route.get("/user/new", UserController.class, "_new"),
			Route.post("/user/create", UserController.class, "create"),
			Route.post("/user/delete/<int:id>", UserController.class, "delete")
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
			Route.get("/user/show/<int:id>", "user.UserController.show"),
			Route.get("/user/edit/<int:id>", "user.UserController.edit"),
			Route.post("/user/update", "user.UserController.update"),
			Route.get("/user/new", "user.UserController._new"),
			Route.post("/user/create", "user.UserController.create"),
			Route.post("/user/delete/<int:id>", "user.UserController.delete")
		);
		return routes;
    }

	public static void main(String[] args) throws Exception {
		int port = 3333;
		if (args.length >= 1) {
			port = Integer.valueOf(args[0]);
		}
		Application app = Application.getInstance();
		app.setRoutes(initRoutes());
		app.listener(port);
		app.setDebug(false);
		System.out.println(port);
		app.start();
    }
}
