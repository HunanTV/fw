package com.hunantv.fw.net;

import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.hunantv.fw.utils.StringUtil;

public class URLParser {
	private URL url;

	private String host;
	private String protocol;
	private int port;
	private String path;
	private List<String> queries = new ArrayList<String>();

	public URLParser(URL url) {
		this.url = url;
		this.init();
	}

	private void init() {
		host = url.getHost();
		protocol = url.getProtocol();
		if (protocol == null)
			protocol = "http";
		port = url.getPort();
		path = url.getPath();
		String queryStr = url.getQuery();
		if (null != queryStr && queryStr.length() > 0) {
			queryStr = URLDecoder.decode(queryStr);
			String[] vs = StringUtil.split(queryStr, "&");
			for (String v : vs) {
				if (v != null && v.trim().length() > 0) {
					queries.add(v);
				}
			}
		}
	}

	public URLParser addQuery(Map<String, Object> queries) {
		if (null != queries) {
			for (Iterator<String> iter = queries.keySet().iterator(); iter.hasNext();) {
				String key = iter.next();
				this.addQuery(key, queries.get(key));
			}
		}
		return this;
	}

	public URLParser addQuery(String key, Object value) {
		queries.add(key + "=" + value.toString());
		return this;
	}

	public String getFullUrl() {
		StringBuilder strb = new StringBuilder();
		strb.append(protocol).append("://").append(host);
		if (port != -1)
			strb.append(":").append(port);
		strb.append(path);
		String qs = StringUtil.join(queries.toArray(new String[0]), "&");
		if (qs != null)
			strb.append("?").append(URLEncoder.encode(qs));
		return strb.toString();
	}

	public String toString() {
		return this.getFullUrl();
	}
}
