package com.hunantv.fw.route;

import java.util.HashMap;
import java.util.Map;

import com.hunantv.fw.exceptions.HttpException;


public class Routes {

	private Map<String, Map<String, Route>> routes = new HashMap<String, Map<String, Route>>();

	public Routes() {
	}

	public Routes(Route... routes) {
		for (Route route : routes) {
			this.add(route);
		}
	}

//	public Routes add(Route route) {
//		String m = route.getRouteMethod().getV();
//		Map<String, Route> tmpMap = this.routes.get(m);
//		if (null == tmpMap) {
//			tmpMap = new HashMap<String, Route>();
//			this.routes.put(m, tmpMap);
//		}
//		tmpMap.put(route.getUriReg(), route);
//		return this;
//	}
//
//	public Route match(String method, String uri) {
//		Map<String, Route> tmpMap = this.routes.get(method);
//		if (null == tmpMap) {
//			return null;
//		}
//		Route route = tmpMap.get(uri);
//		return route;
//	}
	
	public Routes add(Route route) {
		String routeUri = route.getUriReg();
		Map<String, Route> tmpMap = this.routes.get(routeUri);
		if (null == tmpMap) {
			tmpMap = new HashMap<String, Route>();
			this.routes.put(routeUri, tmpMap);
		}
		tmpMap.put(route.getRouteMethod().getV(), route);
		return this;
	}

	public Route match(String method, String uri) throws HttpException {
		Map<String, Route> tmpMap = this.routes.get(uri);
		if (null == tmpMap)
			throw HttpException.err404();
		
		Route route = tmpMap.get(method);
		if (null == route)
			throw HttpException.err405();

		return route;
	}
}
