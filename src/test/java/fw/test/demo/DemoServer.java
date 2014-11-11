package fw.test.demo;

import com.hunantv.fw.Application;
import com.hunantv.fw.route.Route;
import com.hunantv.fw.route.Routes;


public class DemoServer {

    public static Routes initRoutes() {
		Routes routes = new Routes(
			Route.get("/user/index", DemoController.class, "index"),
			Route.get("/user/list", DemoController.class, "list"),
			Route.get("/user/get/<int:id>", DemoController.class, "get"),
			Route.post("/user/save", DemoController.class, "save"),
            Route.post("/user/update/<int:id>", DemoController.class, "update"),
			Route.post("/user/delete/<int:id>", DemoController.class, "delete")
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
			Route.get("/user/index", "fw.test.functional.DemoController.index"),
			Route.get("/user/list", "fw.test.functional.DemoController.list"),
			Route.get("/user/get/<int:id>", "fw.test.functional.DemoController.get"),
			Route.post("/user/save", "fw.test.functional.DemoController.save"),
            Route.post("/user/update/<int:id>", "fw.test.functional.DemoController.update"),
			Route.post("/user/delete/<int:id>", "fw.test.functional.DemoController.delete")
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
