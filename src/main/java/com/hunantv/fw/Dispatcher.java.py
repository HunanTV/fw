package com.hunantv.fw;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hunantv.fw.route.Route;
import com.hunantv.fw.route.Routes;
import com.hunantv.fw.utils.StringUtil;
import com.hunantv.fw.view.View;

public class Dispatcher extends HttpServlet {

	private Application app = Application.getInstance();

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Routes routes = app.getRoutes();
		String uri = StringUtil.ensureEndedWith(request.getRequestURI(), "/");

		Route route = routes.match(request.getMethod(), uri);
		if (route == null) {
			Err404(response);
			return;
		}

		Class<? extends Controller> controllerClass = route.getControllerClass();
		try {
			Method method = controllerClass.getMethod(route.getControllerMethod());
			Controller controller = controllerClass.newInstance();
			controller.setRequest(request);
			controller.setResponse(response);

			View view = (View) method.invoke(controller);
			response.getWriter().write(view.toString());
//		} catch (NoSuchMethodException e) {
//			Err404(response);
//		} catch (SecurityException e) {
//			Err404(response);
//		} catch (InstantiationException e) {
//			Err404(response);
//		} catch (IllegalAccessException e) {
//			Err404(response);
//		} catch (IllegalArgumentException e) {
//			Err404(response);
//		} catch (InvocationTargetException e) {
//			Err404(response);
//		}
		}catch (Exception ex) {
			 ex.printStackTrace();
		 }
	}

	public void Err404(HttpServletResponse response) throws IOException {
		response.sendError(HttpServletResponse.SC_NOT_FOUND);
	}
}