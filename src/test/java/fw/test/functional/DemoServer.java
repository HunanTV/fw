package fw.test.functional;

import com.hunantv.fw.Application;
import com.hunantv.fw.route.Route;
import com.hunantv.fw.route.Routes;


public class DemoServer {

    public static Routes initRoutes() {
		Routes routes = new Routes(
			Route.get("/user/index", UserController.class, "index"),
			Route.get("/user/list", UserController.class, "list"),
			Route.get("/user/get/<int:id>", UserController.class, "get"),
			Route.post("/user/save", UserController.class, "save"),
            Route.post("/user/update/<int:id>", UserController.class, "update"),
			Route.post("/user/delete/<int:id>", UserController.class, "delete")
		);
		return routes;
    }
    
    public static Routes initRoutes2() {
		Routes routes = new Routes(
			Route.get("/user/index", "fw.test.functional.UserController.index"),
			Route.get("/user/list", "fw.test.functional.UserController.list"),
			Route.get("/user/get/<int:id>", "fw.test.functional.UserController.get"),
			Route.post("/user/save", "fw.test.functional.UserController.save"),
            Route.post("/user/update/<int:id>", "fw.test.functional.UserController.update"),
			Route.post("/user/delete/<int:id>", "fw.test.functional.UserController.delete")
		);
		return routes;
    }
    
	public static void main(String[] args) throws Exception {
		Application app = Application.getInstance();
		app.setRoutes(initRoutes());
//		app.setRoutes(initRoutes2());
		app.listener(3333);
		app.start();
    }
}
