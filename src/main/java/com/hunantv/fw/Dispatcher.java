package com.hunantv.fw;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hunantv.fw.route.Routes;
import com.hunantv.fw.utils.StringUtil;
import com.hunantv.fw.view.RedirectView;
import com.hunantv.fw.view.View;

public class Dispatcher extends HttpServlet {

	private Application app = Application.getInstance();

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Routes routes = app.getRoutes();
		String uri = StringUtil.ensureEndedWith(request.getRequestURI(), "/");

		Routes.RouteAndValues rv = routes.match(request.getMethod(), uri);
		if (rv == null) {
			Err404(response);
			return;
		}

		Class<? extends Controller> controllerClass = rv.route.getControllerClass();
		try {
			Method method = controllerClass.getMethod(rv.route.getControllerMethod(), rv.route.getParameterTypeList());

			Controller controller = controllerClass.newInstance();
			controller.setRequest(request);
			controller.setResponse(response);

			View view = (View) method.invoke(controller, rv.getValuedObjectArrays());
			if (view instanceof RedirectView) {
				response.sendRedirect(view.render());
			} else {
				response.getWriter().write(view.render());
			}
		} catch (Exception ex) {
			// catch (NoSuchMethodException e) {}
			// catch (SecurityException e) {}
			// catch (InstantiationException e) {}
			// catch (IllegalAccessException e) {}
			// catch (IllegalArgumentException e) {}
			// catch (InvocationTargetException e) {}
			ex.printStackTrace();
			Err404(response);
		}
	}

	public void Err404(HttpServletResponse response) throws IOException {
		response.sendError(HttpServletResponse.SC_NOT_FOUND);
	}
}