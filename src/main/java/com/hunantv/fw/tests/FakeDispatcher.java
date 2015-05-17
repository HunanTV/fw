package com.hunantv.fw.tests;

import com.hunantv.fw.Dispatcher;
import com.hunantv.fw.route.Routes;

public class FakeDispatcher extends Dispatcher {

	@Override
	public void init() {
	}

	public void setRoutes(Routes routes) {
		this.routes = routes;
	}
}
