package com.hunantv.fw.tests;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hunantv.fw.Dispatcher;
import com.hunantv.fw.exceptions.Http404;
import com.hunantv.fw.exceptions.Http500;
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

	public View get(String url) throws Exception {
		return this.get(url, new HashMap<String, Object>());
	}

	public View get(String url, Map<String, Object> params) throws Exception {
		URLParser urlParser = new URLParser(url);
		urlParser.addQuery(params);

		FakeRequest req = new FakeRequest();
		req.setURLParser(urlParser);
		req.setMethod("GET");
		req.setParameter(urlParser.getQueryPair());

		return dis.doIt(req, null);
	}
	
	public View post(String url) throws Exception {
		return this.post(url, new HashMap<String, Object>());
	}

	public View post(String url, Map<String, Object> params) throws Exception {
		URLParser urlParser = new URLParser(url);
		urlParser.addQuery(params);

		FakeRequest req = new FakeRequest();
		req.setURLParser(urlParser);
		req.setMethod("POST");
		req.setParameter(urlParser.getQueryPair());

		return dis.doIt(req, null);
	}
}
