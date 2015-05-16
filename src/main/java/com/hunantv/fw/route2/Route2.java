package com.hunantv.fw.route2;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hunantv.fw.Controller;
import com.hunantv.fw.exceptions.RouteDefineException;
import com.hunantv.fw.utils.StringUtil;

public class Route2 {

	Map<String, Object[]> classAndRegMapping = new HashMap<String, Object[]>() {
		{
			put("int:", new Object[] { Integer.TYPE, "(\\d+)" });
			put("long:", new Object[] { Long.TYPE, "(\\d+)" });
			put("float:", new Object[] { Long.TYPE, "(\\d+(\\.\\d)?)" });
			put("double:", new Object[] { Long.TYPE, "(\\d+(\\.\\d)?)" });
			put("str:", new Object[] { String.class, "([\\pP\\w\u4E00-\u9FA5]+)" });
			put("string:", new Object[] { String.class, "([\\pP\\w\u4E00-\u9FA5]+)" });
			put("list:", new Object[] { List.class, "([\\w\u4E00-\u9FA5]+(,[\\w\u4E00-\u9FA5]+)*)" });
		}
	};

	Pattern uriP = Pattern.compile("<([a-zA-Z_][a-zA-Z_0-9]*)(:[^>]*)?>");
	Pattern argP = Pattern.compile("^<(int:|long:|float:|double:|str:|string:|list:)?([^>]*)>$");

	private String originUriReg; // 原始传进来的uri, 例如：/save/<name>/<int:age>
	private String uriReg; // 转换后的uri正则, 例如：/save/\\w+/\\d+
	Class<?>[] types; // 转换过程中的类型，例如： { String.class, Integer.TYPE }

	private Class<? extends Controller> controllerClass;
	private String httpMethod;
	private Method controllerAction;

	public Route2(String uriReg, String controllerAndMethod) {
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
			init(uriReg, controllerClass, methodString, "GET");
		} catch (ClassNotFoundException ex) {
			throw new RuntimeException(ex);
		}
	}

	public Route2(String uriReg, Class<? extends Controller> controllerClass, String methodString, String httpMethod) {
		init(uriReg, controllerClass, methodString, "GET");
	}

	protected void init(String uriReg, Class<? extends Controller> controllerClass, String actionString,
	        String httpMethod) {
		this.originUriReg = StringUtil.ensureEndedWith(uriReg, "/");
		this.uriReg = this.originUriReg;
		this.controllerClass = controllerClass;
		this.httpMethod = httpMethod;
		this.controllerAction = initControllerAction(actionString);
	}

	protected Method initControllerAction(String methodString) {
		try {
			List<Class<?>> typeList = new ArrayList<Class<?>>();
			Matcher uriM = uriP.matcher(originUriReg);
			while (uriM.find()) {
				String g = uriM.group();
				Matcher argM = argP.matcher(g);
				if (argM.find()) {
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
			if (typeList.size() > 0) {
				types = typeList.toArray(new Class<?>[0]);
				return controllerClass.getMethod(methodString, types);
			}
			return controllerClass.getMethod(methodString);
		} catch (NoSuchMethodException e) {
			throw new RouteDefineException(e);
		} catch (SecurityException e) {
			throw new RouteDefineException(e);
		}
	}

	public Object[] match(String uri) {
		uri = StringUtil.ensureEndedWith(uri, "/");
		if (uri == this.originUriReg)
			return new Object[0];

		Pattern p = Pattern.compile(uriReg);
		Matcher m = p.matcher(uri);

		if (m.find()) {
			int c = m.groupCount();
			String[] matchStrs = new String[c - 1];
			for (int i = 1; i < c; i++)
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
		return null;
	}

	public Class<? extends Controller> getControllerClass() {
		return controllerClass;
	}

	public String getUriReg() {
		return this.uriReg;
	}

	public String getOriginUriReg() {
		return this.originUriReg;
	}

	public String getHttpMethod() {
		return httpMethod;
	}

	public Method getControllerAction() {
		return this.controllerAction;
	}
}
