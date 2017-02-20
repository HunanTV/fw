package com.hunantv.fw;

import java.io.IOException;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import com.hunantv.fw.exceptions.HttpException;
import com.hunantv.fw.route.Routes;
import com.hunantv.fw.utils.FwLogger;
import com.hunantv.fw.utils.StringUtil;
import com.hunantv.fw.utils.WebUtil;
import com.hunantv.fw.view.View;

public class Dispatcher extends AbstractHandler {

	public final static FwLogger logger = new FwLogger(Dispatcher.class);
	Application app = Application.getInstance();
	protected Routes routes = app.getRoutes();
	protected boolean debug = app.isDebug();

	protected static final MultipartConfigElement MULTI_PART_CONFIG = new MultipartConfigElement(System.getProperty("java.io.tmpdir"));

	@Override
	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		baseRequest.setHandled(true);
		Long btime = System.currentTimeMillis();
		logger.initSeqid();
		logger.debug(target);
		if (WebUtil.isTextHtml(request)) {
			response.setContentType("text/html;charset=UTF-8");
		}
		if (WebUtil.isMultipart(request)) {
			request.setAttribute(Request.__MULTIPART_CONFIG_ELEMENT, MULTI_PART_CONFIG);
		}

		try {
			response.setStatus(HttpServletResponse.SC_OK);
			View view = doIt(target, request, response);
			view.renderTo(response);
		} catch (HttpException ex) {
			this.httpErr(response, ex);
		} catch (Exception ex) {
			this.err500(response, ex);
		} finally {
			Long etime = System.currentTimeMillis();
			logger.delayInfo("cost", Long.toString(etime - btime));
			logger.clearSeqid();
		}
	}

	public View doIt(String target, HttpServletRequest request, HttpServletResponse response) throws HttpException {
		String uri = StringUtil.ensureEndedWith(request.getRequestURI(), "/");
		String queryStr = request.getQueryString();

		logger.delayInfo("uri", uri);
		if (null != queryStr)
			logger.delayInfo("query", queryStr);
		ControllerAndAction controllerAndAction = routes.match(request.getMethod(), uri);
		return controllerAndAction.doAction(request, response);
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
		logger.error(ex, ex);
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}
}