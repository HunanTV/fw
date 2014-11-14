package com.hunantv.fw.route;

import java.util.*;
import java.util.stream.Collectors;

public class Routes {

	private Map<String, List<Route>> routes = new HashMap<String, List<Route>>();

	public Routes(Route... routes) {
		for (Route route : routes) {
			this.add(route);
		}
	}

	public Routes add(Route route) {
		String m = route.getHttpMethod();
		List<Route> tmpRoutes = this.routes.get(m);
		if (null == tmpRoutes) {
			tmpRoutes = new ArrayList<Route>();
			this.routes.put(m, tmpRoutes);
		}
		tmpRoutes.add(route);
		return this;
	}

	public RouteAndValues match(String method, String uri) {
		List<Route> routes = this.routes.get(method.toUpperCase());
		if (null == routes || routes.size() == 0) {
			return null;
		}

        RouteAndValues rv = null;
        for (Route route : routes) {
            Collection<URIMatcher.NamedValue> values = route.matches(uri);
            if (values == null) continue;

            rv = new RouteAndValues(route, values);
            break;
        }
		return rv;
	}

    public static class RouteAndValues {
        public final Route route;
        public final Collection<URIMatcher.NamedValue> values;

        public RouteAndValues(Route route,
                              Collection<URIMatcher.NamedValue> values) {
            this.route = route;
            this.values = values;
        }

        public Object[] getValuedObjectArrays() {
            return this.values
                    .stream()
                    .map( nv -> nv.value)
                    .collect(Collectors.toList())
                    .toArray(new Object[0]);
        }
    }
}
