package fw;

import com.hunantv.fw.Application;
import com.hunantv.fw.route.Route;
import com.hunantv.fw.route.Routes;

public class Main {

    public static Routes initRoutes() {
		Routes routes = new Routes(
			Route.get("/user/index", UserController.class, "index"),
			Route.get("/user/list", UserController.class, "list"),
			Route.post("/user/get", UserController.class, "get"),
			Route.post("/user/save", UserController.class, "save"),
			Route.post("/user/update", UserController.class, "update"),
			Route.post("/user/delete", UserController.class, "delete")
		);
		return routes;
    }

//	public static void main(String[] args) throws Exception {
//		Application app = Application.getInstance();
//		app.setRoutes(initRoutes());
//		app.listener(3030);
//		app.start();
//    }
    
    public static void main(String[] args) throws Exception {
    	
    }
}
