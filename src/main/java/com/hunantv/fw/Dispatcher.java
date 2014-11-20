package com.hunantv.fw;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hunantv.fw.exceptions.HttpException404;
import com.hunantv.fw.exceptions.HttpException500;
import com.hunantv.fw.route.Route;
import com.hunantv.fw.route.Routes;
import com.hunantv.fw.utils.FwLogger;
import com.hunantv.fw.utils.StringUtil;
import com.hunantv.fw.view.RedirectView;
import com.hunantv.fw.view.View;

@SuppressWarnings("serial")
public class Dispatcher extends HttpServlet {

	public final static FwLogger logger = new FwLogger(Dispatcher.class);

	protected Routes routes = null;
	protected boolean debug = false;
	@Override
	public void init() {
		Application app =Application.getInstance(); 
		this.routes = app.getRoutes();
		this.debug = app.isDebug();
	}

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long btime = Calendar.getInstance().getTimeInMillis();
		logger.initSeqid();

		logger.debug(request.getRequestURL());
		try {
			View view = doIt(request, response);
			if (view instanceof RedirectView) {
				response.sendRedirect(view.render());
			} else {
				response.getWriter().write(view.render());
			}
		} catch (HttpException404 ex) {
			this.Err404(response);
		} catch (HttpException500 ex) {
			this.Err500(response, ex);
		} catch (Exception ex) {
			this.Err500(response, ex);
		} finally {
			Long etime = Calendar.getInstance().getTimeInMillis();
			logger.delayInfo("cost", new Long(etime - btime).toString());
			logger.clearSeqid();
		}
	}

	public View doIt(HttpServletRequest request, HttpServletResponse response) throws IOException, HttpException404,
	        HttpException500 {
		String uri = StringUtil.ensureEndedWith(request.getRequestURI(), "/");
		logger.delayInfo("uri", uri);

		Route route = routes.match(request.getMethod(), uri);
		if (route == null) {
			throw new HttpException404();
		}

		Class<? extends Controller> controllerClass = route.getControllerClass();
		Method method = null;
		Controller controller = null;
		try {
			method = controllerClass.getMethod(route.getControllerMethod());

			controller = controllerClass.newInstance();
			controller.setRequest(request);
			controller.setResponse(response);
		} catch (NoSuchMethodException e) {
			throw new HttpException404(e);
		} catch (SecurityException e) {
			throw new HttpException404(e);
		} catch (InstantiationException e) {
			throw new HttpException404(e);
		} catch (IllegalAccessException e) {
			throw new HttpException404(e);
		} catch (IllegalArgumentException e) {
			throw new HttpException404(e);
		}
		try {
			return (View) method.invoke(controller);
		} catch (Exception e) {
			throw new HttpException500(e);
		}
	}

	public void Err404(HttpServletResponse response) throws IOException {
		response.sendError(HttpServletResponse.SC_NOT_FOUND);
	}

	public void Err500(HttpServletResponse response, Exception ex) throws IOException {
		logger.error("500 Internal Server Error", ex);
		if (this.debug) {
			ex.printStackTrace(response.getWriter());
		}
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}
}