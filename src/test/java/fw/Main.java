package fw;

import com.hunantv.fw.Application;
import com.hunantv.fw.Route;
import com.hunantv.fw.Routes;

public class Main {

    public static Routes initRoutes() {
	Routes routes = new Routes(
		new Route("/user/index", UserController.class, "index", Route.Method.GET),
		new Route("/user/list", UserController.class, "list", Route.Method.GET),
		new Route("/user/get", UserController.class, "get", Route.Method.GET),
		new Route("/user/save", UserController.class, "save", Route.Method.POST),
		new Route("/user/update", UserController.class, "update", Route.Method.POST),
		new Route("/user/delete", UserController.class, "delete", Route.Method.POST)
	);
	return routes;
    }

    public static void main(String[] args) throws Exception {
	Application app = Application.getInstance();
	app.setRoutes(initRoutes());
	app.listener(3030);
	app.start();
    }
}
