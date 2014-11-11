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

	FakeDispatcher dis = null;

	public FakeBrowser(Routes routes) {
		dis = new FakeDispatcher();
		dis.setRoutes(routes);
	}

	public View get(String url) {
		return this.get(url, new HashMap<String, Object>());
	}

	public View get(String url, Map<String, Object> params) {
		URLParser urlParser = new URLParser(url);
		urlParser.addQuery(params);
		
		FakeRequest req = new FakeRequest();
		req.setURLParser(urlParser);
		req.setMethod("GET");
		req.setParameter(urlParser.getQueryPair());

		try {
			return dis.doIt(req, null);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
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
