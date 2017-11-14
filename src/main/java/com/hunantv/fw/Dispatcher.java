package com.hunantv.fw;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hunantv.fw.exceptions.HttpException;
import com.hunantv.fw.log.FwLogger;
import com.hunantv.fw.log.LogData;
import com.hunantv.fw.route.Routes;
import com.hunantv.fw.utils.StringUtil;
import com.hunantv.fw.utils.WebUtil;
import com.hunantv.fw.view.View;

public class Dispatcher extends AbstractHandler {

	public FwLogger logger = new FwLogger(Dispatcher.class);

	Application app = Application.getInstance();
	protected Routes routes = app.getRoutes();;
	protected boolean debug = app.isDebug();
	public final static String X_HTTP_TRACEID = "X-HTTP-TRACEID";
	protected static final MultipartConfigElement MULTI_PART_CONFIG = new MultipartConfigElement(System.getProperty("java.io.tmpdir"));

	@Override
	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		baseRequest.setHandled(true);
		Long btime = System.currentTimeMillis();
		String traceId = request.getHeader(X_HTTP_TRACEID);
		if (traceId == null) {
			traceId = request.getParameter("seqid");
		}

		if (traceId != null) {
			LogData.instance().setId(traceId);
			logger.delayInfo("traceId", traceId);
			logger.info("Get traceId from client:" + traceId);
		} else {

		}

		logger.debug(target);
		response.setContentType("text/html;charset=UTF-8");
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
			String uri = StringUtil.ensureEndedWith(request.getRequestURI(), "/");
			String queryStr = request.getQueryString();
			Long etime = System.currentTimeMillis();
			logger.delayInfo("uri", uri);
			logger.delayInfo("query", queryStr);
			logger.delayInfo("cost", new Long(etime - btime).toString());
			logger.clear();
		}
	}

	public View doIt(String target, HttpServletRequest request, HttpServletResponse response) throws HttpException {
		String uri = StringUtil.ensureEndedWith(request.getRequestURI(), "/");
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
		logger.error(ex.toString(), ex);
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}
}