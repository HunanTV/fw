package com.hunantv.fw.route;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.hunantv.fw.ControllerAndAction;
import com.hunantv.fw.exceptions.HttpException;

public class Routes {

	/**
	 * { uri: { httpMethod: Route } }
	 */
	private Map<String, Map<String, Route>> staticRoutes = new HashMap<String, Map<String, Route>>();

	/**
	 * { Route: { httpMethod } }
	 */
	private Map<Route, Set<String>> dyRoutes = new HashMap<Route, Set<String>>();

	public Routes() {
	}

	public Routes(Route... routes) {
		for (Route route : routes) {
			this.add(route);
		}
	}

	public Routes add(Route route) {
		return route.isStaticRule() ? addStatic(route) : addDynamic(route);
	}

	public Routes addStatic(Route route) {
		String routeUri = route.getUriReg();
		Map<String, Route> tmpMap = staticRoutes.get(routeUri);
		if (null == tmpMap) {
			tmpMap = new HashMap<String, Route>();
			staticRoutes.put(routeUri, tmpMap);
		}
		tmpMap.put(route.getHttpMethod().toString(), route);
		return this;
	}

	public Routes addDynamic(Route route) {
		boolean found = false;
		for (Iterator<Route> iter = this.dyRoutes.keySet().iterator(); iter.hasNext();) {
			Route r = iter.next();
			if (r.getUriReg() == route.getUriReg()) {
				found = true;
				Set<String> httpMethods = dyRoutes.get(r);
				httpMethods.add(route.getHttpMethodStr());
				break;
			}
		}
		if (!found) {
			Set<String> httpMethods = new HashSet<String>();
			httpMethods.add(route.getHttpMethodStr());
			this.dyRoutes.put(route, httpMethods);
		}
		return this;
	}

	public ControllerAndAction match(String method, String uri) throws HttpException {
		method = method.toUpperCase();
		Map<String, Route> tmpMap = this.staticRoutes.get(uri);
		if (null != tmpMap) { // 静态路由里面找到
			Route route = tmpMap.get(method);
			if (null == route)
				throw HttpException.err405();
			return route.buildControllerAndAction();
		} else {
			for (Iterator<Route> iter = this.dyRoutes.keySet().iterator(); iter.hasNext();) {
				Route r = iter.next();
				Object[] args = r.match(uri);
				if (null != args) { // 在动态路由里面找到
					Set httpMethods = dyRoutes.get(r);
					if (httpMethods.contains(method)) {
						return r.buildControllerAndAction(args);
					} else {
						throw HttpException.err405();
					}
				}
			}
		}
		throw HttpException.err404();
	}
}
