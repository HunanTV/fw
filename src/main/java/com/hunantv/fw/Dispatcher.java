package com.hunantv.fw;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hunantv.fw.exceptions.Http404;
import com.hunantv.fw.exceptions.Http500;
import com.hunantv.fw.exceptions.HttpException;
import com.hunantv.fw.route.Route;
import com.hunantv.fw.route.Routes;
import com.hunantv.fw.utils.FwLogger;
import com.hunantv.fw.utils.StringUtil;
import com.hunantv.fw.view.HtmlView;
import com.hunantv.fw.view.RedirectView;
import com.hunantv.fw.view.View;

public class Dispatcher extends HttpServlet {

	public final static FwLogger logger = new FwLogger(Dispatcher.class);

	protected Routes routes = null;
	protected boolean debug = false;

	@Override
	public void init() {
		Application app = Application.getInstance();
		this.routes = app.getRoutes();
		this.debug = app.isDebug();
	}

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long btime = Calendar.getInstance().getTimeInMillis();
		logger.initSeqid();

		logger.debug(request.getRequestURL());
		String charset = "UTF-8";
		response.setCharacterEncoding(charset);
		response.setContentType("textml;charset=" + charset);
		try {
			View view = doIt(request, response);
			if (view instanceof RedirectView) {
				response.sendRedirect(view.render());
			} else if (view instanceof HtmlView) {
				view.renderTo(response.getWriter());
			} else {
				response.getWriter().write(view.render());
			}
		} catch (HttpException ex) {
			this.httpErr(response, ex);
		} catch (Exception ex) {
			this.err500(response, ex);
		} finally {
			Long etime = Calendar.getInstance().getTimeInMillis();
			logger.delayInfo("cost", new Long(etime - btime).toString());
			logger.clearSeqid();
		}
	}

	public View doIt(HttpServletRequest request, HttpServletResponse response) throws IOException, HttpException,
	        Http500 {
		String uri = StringUtil.ensureEndedWith(request.getRequestURI(), "/");
		logger.delayInfo("uri", uri);

		Route route = routes.match(request.getMethod(), uri);
		
		ControllerAndMethod controllerAndMethod = route.buildControllerAndMethod();
		controllerAndMethod.controller.setRequest(request);
		controllerAndMethod.controller.setResponse(response);
		
		try {
			return (View) controllerAndMethod.method.invoke(controllerAndMethod.controller);
		} catch (Exception e) {
			throw new Http500(e);
		}
	}

	public void httpErr(HttpServletResponse response, HttpException ex) throws IOException {
		int code = ex.getCode();
		if (HttpServletResponse.SC_INTERNAL_SERVER_ERROR == code) {
			err500(response, ex);
		} else {
			response.sendError(ex.getCode());
		}
	}

	public void err500(HttpServletResponse response, Exception ex) throws IOException {
		if (this.debug) {
			ex.printStackTrace(response.getWriter());
		}
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}
}