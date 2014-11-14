package com.hunantv.fw.route;

import java.util.Collection;

import com.hunantv.fw.Controller;
import com.hunantv.fw.utils.StringUtil;

public class Route {

	private String httpMethod;
	private String uriReg;
	private Class<? extends Controller> controllerClass;
	private String controllerMethod;
	private URIMatcher matcher;

	public Route(String uriReg, String controllerAndMethod) {
		this(uriReg, controllerAndMethod, "GET");
	}

	public Route(String uriReg, String controllerAndMethod, String httpMethod) {
		String[] vs = StringUtil.split(controllerAndMethod, ".");
		if (vs.length < 2) {
			throw new RuntimeException();
		}
		String[] tmp = new String[vs.length - 1];
		for (int i = 0; i < tmp.length; i++) {
			tmp[i] = vs[i];
		}
		String controllerString = StringUtil.join(tmp, ".");
		String methodString = vs[vs.length - 1];
		try {
			Class<? extends Controller> controllerClass = (Class<? extends Controller>) Class.forName(controllerString);
			init(uriReg, controllerClass, methodString, httpMethod);
		} catch (ClassNotFoundException ex) {
			throw new RuntimeException(ex);
		}
	}

	public Route(String uriReg, Class<? extends Controller> controllerClass, String controllerMethod) {
		this(uriReg, controllerClass, controllerMethod, "GET");
	}

	public Route(String uriReg, Class<? extends Controller> controllerClass, String controllerMethod, String httpMethod) {
		init(uriReg, controllerClass, controllerMethod, httpMethod);
	}

	private void init(String uriReg, Class<? extends Controller> controllerClass, String controllerMethod,
	        String httpMethod) {
		this.uriReg = StringUtil.ensureEndedWith(uriReg, "/");
		this.httpMethod = httpMethod;
		this.controllerClass = controllerClass;
		this.controllerMethod = controllerMethod;
		this.matcher = MongoURITemplateProcessor.process(this.uriReg);
	}

	public Class[] getParameterTypeList() {
		return this.matcher.getParameterTypeList();
	}

	public Collection<URIMatcher.NamedValue> matches(String uri) {
		return this.matcher.matches(uri);
	}

	public static Route get(String uriReg, String controllerAndMethod) {
		return new Route(uriReg, controllerAndMethod, "GET");
	}

	public static Route get(String uriReg, Class<? extends Controller> controllerClass, String controllerMethod) {
		return new Route(uriReg, controllerClass, controllerMethod, "GET");
	}

	public static Route post(String uriReg, String controllerAndMethod) {
		return new Route(uriReg, controllerAndMethod, "POST");
	}

	public static Route post(String uriReg, Class<? extends Controller> controllerClass, String controllerMethod) {
		return new Route(uriReg, controllerClass, controllerMethod, "POST");
	}

	public static Route put(String uriReg, String controllerAndMethod) {
		return new Route(uriReg, controllerAndMethod, "PUT");
	}

	public static Route put(String uriReg, Class<? extends Controller> controllerClass, String controllerMethod) {
		return new Route(uriReg, controllerClass, controllerMethod, "PUT");
	}

	public static Route delete(String uriReg, String controllerAndMethod) {
		return new Route(uriReg, controllerAndMethod, "DELETE");
	}

	public static Route delete(String uriReg, Class<? extends Controller> controllerClass, String controllerMethod) {
		return new Route(uriReg, controllerClass, controllerMethod, "DELETE");
	}

	public static Route options(String uriReg, String controllerAndMethod) {
		return new Route(uriReg, controllerAndMethod, "OPTIONS");
	}

	public static Route options(String uriReg, Class<? extends Controller> controllerClass, String controllerMethod) {
		return new Route(uriReg, controllerClass, controllerMethod, "OPTIONS");
	}

	public static Route head(String uriReg, String controllerAndMethod) {
		return new Route(uriReg, controllerAndMethod, "HEAD");
	}

	public static Route head(String uriReg, Class<? extends Controller> controllerClass, String controllerMethod) {
		return new Route(uriReg, controllerClass, controllerMethod, "HEAD");
	}

	public static Route trace(String uriReg, String controllerAndMethod) {
		return new Route(uriReg, controllerAndMethod, "TRACE");
	}

	public static Route trace(String uriReg, Class<? extends Controller> controllerClass, String controllerMethod) {
		return new Route(uriReg, controllerClass, controllerMethod, "TRACE");
	}

	public String getHttpMethod() {
		return httpMethod;
	}

	public String getUriReg() {
		return uriReg;
	}

	public Class<? extends Controller> getControllerClass() {
		return controllerClass;
	}

	public String getControllerMethod() {
		return controllerMethod;
	}
}
