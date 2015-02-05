package com.hunantv.fw.route;

import com.hunantv.fw.Controller;
import com.hunantv.fw.ControllerAndAction;
import com.hunantv.fw.exceptions.HttpException;
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
	private String actionStr;

	public Route(String uriReg, String controllerAndActionStr) {
		this(uriReg, controllerAndActionStr, Method.GET);
	}

	public Route(String uriReg, String controllerAndActionStr, Method httpMethod) {
		String[] vs = StringUtil.split(controllerAndActionStr, ".");
		if (vs.length < 2) {
			throw new RuntimeException();
		}
		String[] tmp = new String[vs.length - 1];
		for (int i = 0; i < tmp.length; i++) {
			tmp[i] = vs[i];
		}
		String controllerString = StringUtil.join(tmp, ".");
		String actionString = vs[vs.length - 1];
		try {
			Class<? extends Controller> controllerClass = (Class<? extends Controller>) Class.forName(controllerString);
			init(uriReg, controllerClass, actionString, httpMethod);
		} catch (ClassNotFoundException ex) {
			throw new RuntimeException(ex);
		}
	}

	public Route(String uriReg, Class<? extends Controller> controllerClass, String actionStr) {
		this(uriReg, controllerClass, actionStr, Method.GET);
	}

	public Route(String uriReg, Class<? extends Controller> controllerClass, String actionStr, Method routeMethod) {
		init(uriReg, controllerClass, actionStr, routeMethod);
	}

	private void init(String uriReg, Class<? extends Controller> controllerClass, String actionStr, Method httpMethod) {
		this.uriReg = StringUtil.ensureEndedWith(uriReg, "/");
		this.routeMethod = httpMethod;
		this.controllerClass = controllerClass;
		this.actionStr = actionStr;

	}

	public Method getRouteMethod() {
		return routeMethod;
	}

	public String getHttpMethod() {
		return routeMethod.getV();
	}

	public static Route get(String uriReg, String controllerAndMethod) {
		return new Route(uriReg, controllerAndMethod, Method.GET);
	}

	public static Route get(String uriReg, Class<? extends Controller> controllerClass, String controllerMethod) {
		return new Route(uriReg, controllerClass, controllerMethod, Method.GET);
	}

	public static Route post(String uriReg, String controllerAndMethod) {
		return new Route(uriReg, controllerAndMethod, Method.POST);
	}

	public static Route post(String uriReg, Class<? extends Controller> controllerClass, String controllerMethod) {
		return new Route(uriReg, controllerClass, controllerMethod, Method.POST);
	}

	public static Route put(String uriReg, String controllerAndMethod) {
		return new Route(uriReg, controllerAndMethod, Method.PUT);
	}

	public static Route put(String uriReg, Class<? extends Controller> controllerClass, String controllerMethod) {
		return new Route(uriReg, controllerClass, controllerMethod, Method.PUT);
	}

	public static Route delete(String uriReg, String controllerAndMethod) {
		return new Route(uriReg, controllerAndMethod, Method.DELETE);
	}

	public static Route delete(String uriReg, Class<? extends Controller> controllerClass, String controllerMethod) {
		return new Route(uriReg, controllerClass, controllerMethod, Method.DELETE);
	}

	public static Route options(String uriReg, String controllerAndMethod) {
		return new Route(uriReg, controllerAndMethod, Method.OPTIONS);
	}

	public static Route options(String uriReg, Class<? extends Controller> controllerClass, String controllerMethod) {
		return new Route(uriReg, controllerClass, controllerMethod, Method.OPTIONS);
	}

	public static Route head(String uriReg, String controllerAndMethod) {
		return new Route(uriReg, controllerAndMethod, Method.HEAD);
	}

	public static Route head(String uriReg, Class<? extends Controller> controllerClass, String controllerMethod) {
		return new Route(uriReg, controllerClass, controllerMethod, Method.HEAD);
	}

	public static Route trace(String uriReg, String controllerAndMethod) {
		return new Route(uriReg, controllerAndMethod, Method.TRACE);
	}

	public static Route trace(String uriReg, Class<? extends Controller> controllerClass, String controllerMethod) {
		return new Route(uriReg, controllerClass, controllerMethod, Method.TRACE);
	}

	public String getUriReg() {
		return uriReg;
	}

	public Class<? extends Controller> getControllerClass() {
		return controllerClass;
	}

	public String getActionStr() {
		return actionStr;
	}

	public ControllerAndAction buildControllerAndAction() throws HttpException {

		Class<? extends Controller> controllerClass = this.getControllerClass();
		java.lang.reflect.Method method = null;
		Controller controller = null;
		try {
			method = controllerClass.getMethod(this.getActionStr());
			controller = controllerClass.newInstance();
			return new ControllerAndAction(controller, method);
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
		        | IllegalArgumentException ex) {
			throw HttpException.err404();
		}
	}
}
