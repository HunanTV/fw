package com.hunantv.fw;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hunantv.fw.route.Routes;
import com.hunantv.fw.utils.FwLogger;
import com.hunantv.fw.utils.StringUtil;
import com.hunantv.fw.view.RedirectView;
import com.hunantv.fw.view.View;

@SuppressWarnings("serial")
public class Dispatcher extends HttpServlet {

	public final static FwLogger logger = new FwLogger(Dispatcher.class);

	private Application app = Application.getInstance();

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long btime = Calendar.getInstance().getTimeInMillis();
		logger.initSeqid();
		
		logger.debug(request.getRequestURL());
		String uri = StringUtil.ensureEndedWith(request.getRequestURI(), "/");
		logger.delayInfo("uri", uri);
		try {
			logger.debug(request.getRequestURL());

			Routes routes = app.getRoutes();
			Routes.RouteAndValues rv = routes.match(request.getMethod(), uri);
			if (rv == null) {
				Err404(response);
				return;
			}

			Class<? extends Controller> controllerClass = rv.route.getControllerClass();
			Method method = null;
			Controller controller = null;
			try {
				method = controllerClass.getMethod(rv.route.getControllerMethod(), rv.route.getParameterTypeList());

				controller = controllerClass.newInstance();
				controller.setRequest(request);
				controller.setResponse(response);
			} catch (NoSuchMethodException e) {
				Err404(response);
			} catch (SecurityException e) {
				Err404(response);
			} catch (InstantiationException e) {
				Err404(response);
			} catch (IllegalAccessException e) {
				Err404(response);
			} catch (IllegalArgumentException e) {
				Err404(response);
			}
			try {
				View view = (View) method.invoke(controller, rv.getValuedObjectArrays());
				if (view instanceof RedirectView) {
					response.sendRedirect(view.render());
				} else {
					response.getWriter().write(view.render());
				}
			} catch (Exception ex) {
				Err500(response, ex);
			}
		} finally {
			Long etime = Calendar.getInstance().getTimeInMillis();
			logger.delayInfo("cost", new Long(etime - btime).toString());
			logger.clearSeqid();
		}
	}

	public void Err404(HttpServletResponse response) throws IOException {
		response.sendError(HttpServletResponse.SC_NOT_FOUND);
	}

	public void Err500(HttpServletResponse response, Exception ex) throws IOException {
		logger.error("500 Internal Server Error", ex);
		ex.printStackTrace(response.getWriter());
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}
}