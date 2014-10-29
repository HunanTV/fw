package com.hunantv.fw.route;

import com.hunantv.fw.Controller;
import com.hunantv.fw.utils.StringUtil;

public class Route {

	public static class Method {
		public static final Method GET = new Method("GET");
		public static final Method POST = new Method("POST");
		public static final Method PUT = new Method("PUT");
		public static final Method DELETE = new Method("DELETE");
		public static final Method OPTIONS = new Method("OPTIONS");
		public static final Method HEAD = new Method("HEAD");
		public static final Method TRACE = new Method("TRACE");
		private String v;

		private Method() {
		}

		private Method(String v) {
			this.v = v;
		}

		public String getV() {
			return v;
		}
	}

	private Method routeMethod;
	private String uriReg;
	private Class<? extends Controller> controllerClass;
	private String controllerMethod;

	public Route(String uriReg, Class<? extends Controller> controllerClass, String controllerMethod) {
		this(uriReg, controllerClass, controllerMethod, Method.GET);
	}

	public Route(String uriReg, Class<? extends Controller> controllerClass, String controllerMethod, Method routeMethod) {

		this.uriReg = StringUtil.append(uriReg, "/");
		this.routeMethod = routeMethod;
		this.controllerClass = controllerClass;
		this.controllerMethod = controllerMethod;

	}

	public static Route get(String uriReg, Class<? extends Controller> controllerClass, String controllerMethod) {
		return new Route(uriReg, controllerClass, controllerMethod, Method.GET);
	}

	public static Route post(String uriReg, Class<? extends Controller> controllerClass, String controllerMethod) {
		return new Route(uriReg, controllerClass, controllerMethod, Method.POST);
	}

	public static Route put(String uriReg, Class<? extends Controller> controllerClass, String controllerMethod) {
		return new Route(uriReg, controllerClass, controllerMethod, Method.PUT);
	}

	public static Route delete(String uriReg, Class<? extends Controller> controllerClass, String controllerMethod) {
		return new Route(uriReg, controllerClass, controllerMethod, Method.DELETE);
	}

	public static Route options(String uriReg, Class<? extends Controller> controllerClass, String controllerMethod) {
		return new Route(uriReg, controllerClass, controllerMethod, Method.OPTIONS);
	}

	public static Route head(String uriReg, Class<? extends Controller> controllerClass, String controllerMethod) {
		return new Route(uriReg, controllerClass, controllerMethod, Method.HEAD);
	}

	public static Route trace(String uriReg, Class<? extends Controller> controllerClass, String controllerMethod) {
		return new Route(uriReg, controllerClass, controllerMethod, Method.TRACE);
	}

	public Method getRouteMethod() {
		return routeMethod;
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
