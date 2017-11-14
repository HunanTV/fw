package com.hunantv.fw.tests;

import java.util.HashMap;
import java.util.Map;

import com.hunantv.fw.exceptions.HttpException;
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

	public View get(String url) throws HttpException {
		return this.get(url, new HashMap<>(), null);
	}

	public View get(String url, Map<String, Object> params) throws HttpException {
		return this.get(url, params, null);
	}

	public View get(String url, Map<String, Object> params, Map<String, String> headers) throws HttpException {
		URLParser urlParser = new URLParser(url);
		urlParser.addQuery(params);

		FakeRequest req = new FakeRequest();
		req.setURLParser(urlParser);
		req.setMethod("GET");
		req.setParameter(urlParser.getQueryPair());
		req.setHeaders(headers);

		return dis.doIt(url, req, null);
	}
	
	public View post(String url) throws HttpException {
		return this.post(url, new HashMap<>(), null);
	}

	public View post(String url, Map<String, Object> params) throws HttpException {
	    return this.post(url, params, null);
	}

	public View post(String url, Map<String, Object> params, Map<String, String> headers) throws HttpException {
		URLParser urlParser = new URLParser(url);
		urlParser.addQuery(params);

		FakeRequest req = new FakeRequest();
		req.setURLParser(urlParser);
		req.setMethod("POST");
		req.setParameter(urlParser.getQueryPair());
		req.setHeaders(headers);

		return dis.doIt(url, req, null);
	}
}
