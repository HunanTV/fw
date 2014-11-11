package com.hunantv.fw.tests;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hunantv.fw.Dispatcher;
import com.hunantv.fw.net.URLParser;
import com.hunantv.fw.route.Routes;
import com.hunantv.fw.view.View;

/**
 * @TODO: 需要考虑put, delete
 */
public class FakeBrowser {
	
	private Routes routes;

	public FakeBrowser(Routes routes) {
		this.routes = routes;
	}
	
	public View get(String url, Map<String, Object> params) {
		FakeDispatcher dis = new FakeDispatcher();
		
		Map<String, String> m = new HashMap<String, String>();
		URLParser urlParser = new URLParser(url);
		
		FakeRequest req = new FakeRequest();
		req.setParameter(urlParser.getQueryPair());
		return dis.doIt(req, null);
	}

	// public Response get(String url, Map<String, String> params) {
	//
	// }

	// public void post() {
	//
	// }
	//
	// public void
}
