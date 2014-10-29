package com.hunantv.fw;

import java.util.HashMap;
import java.util.Map;

public class Routes {

    private Map<String, Map<String, Route>> routes = new HashMap<String, Map<String, Route>>();

    public Routes() {
    }

    public Routes(Route... routes) {
	for (Route route : routes) {
	    this.add(route);
	}
    }

    public Routes add(Route route) {
	String m = route.getRouteMethod().getV();
	Map<String, Route> tmpMap = this.routes.get(m);
	if (null == tmpMap) {
	    tmpMap = new HashMap<String, Route>();
	    this.routes.put(m, tmpMap);
	}
	tmpMap.put(route.getUriReg(), route);
	return this;
    }

    public Route match(String method, String uri) {
	Map<String, Route> tmpMap = this.routes.get(method);
	if (null == tmpMap) {
	    return null;
	}
	Route route = tmpMap.get(uri);
	return route;
    }
}
