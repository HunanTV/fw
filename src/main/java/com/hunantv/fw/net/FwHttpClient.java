package com.hunantv.fw.net;

import com.alibaba.fastjson.JSON;
import com.hunantv.fw.Dispatcher;
import com.hunantv.fw.log.LogData;
import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.SocketConfig;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class FwHttpClient {

	private static CloseableHttpClient client = null;

	static {
		SocketConfig socketConfig = SocketConfig.custom().setSoTimeout(3000).setSoKeepAlive(true).build();
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(3000, TimeUnit.MILLISECONDS);
		cm.setMaxTotal(10000);
		cm.setDefaultMaxPerRoute(2000);
		cm.setDefaultSocketConfig(socketConfig);
		client = HttpClients.custom().setConnectionManager(cm).build();
	}

	public static FwHttpResponse get(String url) throws Exception {
		return get(url, null);
	}

	public static FwHttpResponse get(String url, Map<String, Object> params) throws Exception {
		return get(url, params, -1);
	}

	public static FwHttpResponse get(String url, Map<String, Object> params, Map<String, String> httpHeaders) throws Exception {
		return get(url, params, -1, httpHeaders);
	}

	public static FwHttpResponse get(String url, Map<String, Object> params, int connTimeoutSec, Map<String, String> httpHeaders) throws Exception {
		URLParser urlParser = new URLParser(url);
		if(!params.containsKey("s")) {
			params.put("s", LogData.instance().getId());
		}
		urlParser.addQuery(params);

		HttpGet httpGet = new HttpGet(urlParser.getFullUrl());
		if (connTimeoutSec > 0) {
			RequestConfig config = RequestConfig.custom().setSocketTimeout(connTimeoutSec * 1000).setConnectTimeout(connTimeoutSec * 1000).build();
			httpGet.setConfig(config);
		}

		if (httpHeaders != null && httpHeaders.size() > 0) {
			httpHeaders.forEach((name, value) -> {
				httpGet.addHeader(name, value);
			});
		}
		String id = LogData.instance().getId();
		httpGet.addHeader(Dispatcher.X_HTTP_TRACEID, id);
		// System.out.println("baidu-seqid = " + id);

		try (CloseableHttpResponse response = client.execute(httpGet)) {
			String content = EntityUtils.toString(response.getEntity(), "UTF-8");
			return new FwHttpResponse(response.getStatusLine().getStatusCode(), content);
		}
	}

	public static FwHttpResponse get(String url, Map<String, Object> params, int connTimeoutSec) throws Exception {
		return get(url, params, connTimeoutSec, null);
	}

	public static FwHttpResponse post(String url) throws Exception {
		return post(url, null);
	}

	public static FwHttpResponse post(String url, Map<String, Object> params) throws Exception {
		return post(url, params, -1);
	}

	public static FwHttpResponse post(String url, Map<String, Object> params, Map<String, String> httpHeaders) throws Exception {
		return post(url, params, -1, httpHeaders);
	}

	public static FwHttpResponse post(String url, Map<String, Object> params, int connTimeoutSec) throws Exception {
		return post(url, params, connTimeoutSec, null);
	}

	public static FwHttpResponse postBody(String url, Map<String, Object> params, int connTimeoutSec, Map<String, String> httpHeaders) throws Exception {
		String payload = JSON.toJSONString(params);
		URLParser urlParser = new URLParser(url);
		if(!params.containsKey("s")) {
			params.put("s", LogData.instance().getId());
		}
		HttpPost httpPost = new HttpPost(urlParser.getFullUrl());

		if (connTimeoutSec > 0) {
			RequestConfig config = RequestConfig.custom().setSocketTimeout(connTimeoutSec * 1000).setConnectTimeout(connTimeoutSec * 1000).build();
			httpPost.setConfig(config);
		}
		if (null != params) {
			StringEntity entity = new StringEntity(payload, Consts.UTF_8);
			entity.setContentEncoding("UTF-8");
			httpPost.setEntity(entity);
		}

		if (httpHeaders != null && httpHeaders.size() > 0) {
			httpHeaders.forEach((name, value) -> {
				httpPost.addHeader(name, value);
			});
		}
		String id = LogData.instance().getId();
		httpPost.addHeader(Dispatcher.X_HTTP_TRACEID, id);
		// System.out.println("baidu-seqid = " + id);

		try (CloseableHttpResponse response = client.execute(httpPost)) {
			String content = EntityUtils.toString(response.getEntity(), "UTF-8");
			return new FwHttpResponse(response.getStatusLine().getStatusCode(), content);
		}
	}

	public static FwHttpResponse post(String url, Map<String, Object> params, int connTimeoutSec, Map<String, String> httpHeaders) throws Exception {

		URLParser urlParser = new URLParser(url);
		if(!params.containsKey("s")) {
			params.put("s", LogData.instance().getId());
		}
		HttpPost httpPost = new HttpPost(urlParser.getFullUrl());

		if (connTimeoutSec > 0) {
			RequestConfig config = RequestConfig.custom().setSocketTimeout(connTimeoutSec * 1000).setConnectTimeout(connTimeoutSec * 1000).build();
			httpPost.setConfig(config);
		}
		if (null != params) {
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();
			for (Iterator<String> iter = params.keySet().iterator(); iter.hasNext();) {
				String key = iter.next();
				Object obj = params.get(key);
				String value = "";
				if (null != obj)
					value = obj.toString();

				formparams.add(new BasicNameValuePair(key, value));
			}
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
			httpPost.setEntity(entity);
		}

		if (httpHeaders != null && httpHeaders.size() > 0) {
			httpHeaders.forEach((name, value) -> {
				httpPost.addHeader(name, value);
			});
		}
		String id = LogData.instance().getId();
		httpPost.addHeader(Dispatcher.X_HTTP_TRACEID, id);
		// System.out.println("baidu-seqid = " + id);

		try (CloseableHttpResponse response = client.execute(httpPost)) {
			String content = EntityUtils.toString(response.getEntity(), "UTF-8");
			return new FwHttpResponse(response.getStatusLine().getStatusCode(), content);
		}
	}
}
