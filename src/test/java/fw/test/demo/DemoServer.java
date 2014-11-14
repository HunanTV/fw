package fw.test.demo;

import com.hunantv.fw.Application;
import com.hunantv.fw.route.Route;
import com.hunantv.fw.route.Routes;


public class DemoServer {

    public static Routes initRoutes() {
		Routes routes = new Routes(
			Route.get("/demo/index", DemoController.class, "index"),
			Route.get("/demo/list", DemoController.class, "list"),
            Route.post("/demo/update", DemoController.class, "update"),
            Route.get("/demo/500err", DemoController.class, "err500")
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
			Route.get("/demo/index", "fw.test.functional.DemoController.index"),
			Route.get("/demo/list", "fw.test.functional.DemoController.list"),
            Route.post("/demo/update", "fw.test.functional.DemoController.update")
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
