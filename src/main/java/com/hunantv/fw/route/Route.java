package com.hunantv.fw.route;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hunantv.fw.Controller;
import com.hunantv.fw.ControllerAndAction;
import com.hunantv.fw.exceptions.HttpException;
import com.hunantv.fw.exceptions.RouteDefineException;
import com.hunantv.fw.utils.StringUtil;

public class Route {
	public static enum HttpMethod {
		GET, POST, PUT, DELETE, OPTIONS, HEAD, TRACE;
	}

	Map<String, Object[]> classAndRegMapping = new HashMap<String, Object[]>() {
		{
			put("int:", new Object[] { Integer.TYPE, "(\\d+)" });
			put("long:", new Object[] { Long.TYPE, "(\\d+)" });
			put("float:", new Object[] { Float.TYPE, "(\\d+(?:\\.\\d)?)" });
			put("double:", new Object[] { Double.TYPE, "(\\d+(?:\\.\\d)?)" });
			put("str:", new Object[] { String.class, "([\\pP\\w\u4E00-\u9FA5]+)" });
			put("string:", new Object[] { String.class, "([\\pP\\w\u4E00-\u9FA5]+)" });
			put("list:", new Object[] { List.class, "([\\w\u4E00-\u9FA5]+(?:,[\\w\u4E00-\u9FA5]+)*)" });
		}
	};

	Pattern uriP = Pattern.compile("<([a-zA-Z_][a-zA-Z_0-9]*)(:[^>]*)?>");
	Pattern argP = Pattern.compile("^<(int:|long:|float:|double:|str:|string:|list:)?([^>]*)>$");

	private String rule; // 传进来的rule, 例如：/save/<name>/<int:age>
	private String uriReg; // 转换后的uri正则, 例如：/save/\\w+/\\d+
	Class<?>[] types; // 转换过程中的类型，例如： { String.class, Integer.TYPE }
	private boolean staticRule = true; // 是否是静态的rule。如果不含有正则，则是的，否则不是

	private Class<? extends Controller> controller;
	private HttpMethod httpMethod;
	private Method action;

	private Pattern matchP;

	public static Route get(String uriReg, String controllerAndAction) {
		return new Route(uriReg, controllerAndAction, HttpMethod.GET);
	}

	public static Route get(String uriReg, Class<? extends Controller> controllerClass, String controllerMethod) {
		return new Route(uriReg, controllerClass, controllerMethod, HttpMethod.GET);
	}

	public static Route post(String uriReg, String controllerAndAction) {
		return new Route(uriReg, controllerAndAction, HttpMethod.POST);
	}

	public static Route post(String uriReg, Class<? extends Controller> controllerClass, String controllerMethod) {
		return new Route(uriReg, controllerClass, controllerMethod, HttpMethod.POST);
	}

	public static Route put(String uriReg, String controllerAndAction) {
		return new Route(uriReg, controllerAndAction, HttpMethod.PUT);
	}

	public static Route put(String uriReg, Class<? extends Controller> controllerClass, String controllerMethod) {
		return new Route(uriReg, controllerClass, controllerMethod, HttpMethod.PUT);
	}

	public static Route delete(String uriReg, String controllerAndAction) {
		return new Route(uriReg, controllerAndAction, HttpMethod.DELETE);
	}

	public static Route delete(String uriReg, Class<? extends Controller> controllerClass, String controllerMethod) {
		return new Route(uriReg, controllerClass, controllerMethod, HttpMethod.DELETE);
	}

	public static Route options(String uriReg, String controllerAndAction) {
		return new Route(uriReg, controllerAndAction, HttpMethod.OPTIONS);
	}

	public static Route options(String uriReg, Class<? extends Controller> controllerClass, String controllerMethod) {
		return new Route(uriReg, controllerClass, controllerMethod, HttpMethod.OPTIONS);
	}

	public static Route head(String uriReg, String controllerAndAction) {
		return new Route(uriReg, controllerAndAction, HttpMethod.HEAD);
	}

	public static Route head(String uriReg, Class<? extends Controller> controllerClass, String controllerMethod) {
		return new Route(uriReg, controllerClass, controllerMethod, HttpMethod.HEAD);
	}

	public static Route trace(String uriReg, String controllerAndAction) {
		return new Route(uriReg, controllerAndAction, HttpMethod.TRACE);
	}

	public static Route trace(String uriReg, Class<? extends Controller> controllerClass, String controllerMethod) {
		return new Route(uriReg, controllerClass, controllerMethod, HttpMethod.TRACE);
	}

	public Route(String uriReg, String controllerAndAction, HttpMethod httpMethod) {
		String[] vs = StringUtil.split(controllerAndAction, ".");
		if (vs.length < 2) {
			throw new RuntimeException();
		}
		String[] tmp = new String[vs.length - 1];
		for (int i = 0; i < tmp.length; i++) {
			tmp[i] = vs[i];
		}
		String controllerStr = StringUtil.join(tmp, ".");
		String actionStr = vs[vs.length - 1];
		try {
			Class<? extends Controller> controllerClass = (Class<? extends Controller>) Class.forName(controllerStr);
			init(uriReg, controllerClass, actionStr, httpMethod);
		} catch (ClassNotFoundException ex) {
			throw new RuntimeException(ex);
		}
	}

	public Route(String uriReg, Class<? extends Controller> controllerClass, String methodString, HttpMethod httpMethod) {
		init(uriReg, controllerClass, methodString, httpMethod);
	}

	protected void init(String uriReg, Class<? extends Controller> controllerClass, String actionString,
	        HttpMethod httpMethod) {
		this.rule = StringUtil.ensureEndedWith(uriReg, "/");
		this.uriReg = this.rule;
		this.controller = controllerClass;
		this.httpMethod = httpMethod;
		this.action = initControllerAction(actionString);
	}

	protected Method initControllerAction(String methodString) {
		try {
			List<Class<?>> typeList = new ArrayList<Class<?>>();
			Matcher uriM = uriP.matcher(rule);
			while (uriM.find()) {
				staticRule = false;
				String g = uriM.group();
				Matcher argM = argP.matcher(g);
				if (argM.matches()) {
					String type = argM.group(1);
					if (null == type)
						type = "str:";
					type = type.toLowerCase();
					if (!classAndRegMapping.containsKey(type)) {
						throw new RouteDefineException("Can not support type[" + type + "]");
					}
					Object[] classAndReg = this.classAndRegMapping.get(type);
					typeList.add((Class<?>) classAndReg[0]);
					uriReg = StringUtil.replaceFirst(uriReg, g, (String) classAndReg[1]);
				}
			}
			if (!staticRule) {
				this.matchP = Pattern.compile(uriReg);
			}
			
			if (typeList.size() > 0) {
				types = typeList.toArray(new Class<?>[0]);
				return controller.getMethod(methodString, types);
			}
			return controller.getMethod(methodString);
		} catch (NoSuchMethodException e) {
			throw new RouteDefineException(e);
		} catch (SecurityException e) {
			throw new RouteDefineException(e);
		}
	}

	public Object[] match(String uri) {
		uri = StringUtil.ensureEndedWith(uri, "/");
		if (uri == this.rule)
			return new Object[0];

		Matcher m = matchP.matcher(uri);
		if (!m.matches())
			return null;

		int c = m.groupCount();
		String[] matchStrs = new String[c];
		for (int i = 1; i <= c; i++)
			matchStrs[i - 1] = m.group(i);

		Object[] objects = new Object[matchStrs.length];
		for (int i = 0; i < matchStrs.length; i++) {
			if (types[i] == Integer.TYPE)
				objects[i] = Integer.valueOf(matchStrs[i]);
			else if (types[i] == Long.TYPE)
				objects[i] = Long.valueOf(matchStrs[i]);
			else if (types[i] == Float.TYPE)
				objects[i] = Float.valueOf(matchStrs[i]);
			else if (types[i] == Double.TYPE)
				objects[i] = Double.valueOf(matchStrs[i]);
			else if (types[i] == String.class)
				objects[i] = matchStrs[i];
			else if (types[i] == List.class)
				objects[i] = Arrays.asList(StringUtil.split(matchStrs[i], ","));
		}
		return objects;
	}

	public Class<? extends Controller> getController() {
		return controller;
	}

	public String getUriReg() {
		return this.uriReg;
	}

	public String getRule() {
		return this.rule;
	}

	public HttpMethod getHttpMethod() {
		return httpMethod;
	}

	public String getHttpMethodStr() {
		return httpMethod.toString();
	}

	public Method getAction() {
		return this.action;
	}

	public boolean isStaticRule() {
		return this.staticRule;
	}

	public ControllerAndAction buildControllerAndAction() throws HttpException {
		return buildControllerAndAction(null);
	}

	public ControllerAndAction buildControllerAndAction(Object[] args) throws HttpException {
		Class<? extends Controller> controllerClass = this.getController();
		Controller controller = null;
		try {
			controller = controllerClass.newInstance();
			if (args == null)
				return new ControllerAndAction(controller, this.action);
			return new ControllerAndAction(controller, this.action, args);
		} catch (SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException ex) {
			throw HttpException.err404();
		}
	}
}
