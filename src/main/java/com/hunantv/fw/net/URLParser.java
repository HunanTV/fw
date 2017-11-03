package com.hunantv.fw.net;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hunantv.fw.utils.StringUtil;

public class URLParser {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private URL url;
    private String host;
    private String protocol;
    private int port;
    private String path;
    private String charset = "UTF-8";
    private List<String> queries = new ArrayList<String>();

    public URLParser(String url) {
        this(buildURL(url));
    }

    public URLParser(URL url) {
        this.url = url;
        this.init();
    }

    private void init() {
        host = url.getHost();
        protocol = url.getProtocol();
        if (protocol == null) {
            protocol = "http";
        }
        port = url.getPort();
        path = url.getPath();
        String queryStr = url.getQuery();
        if (null != queryStr && queryStr.length() > 0) {
            String[] vs = StringUtil.split(queryStr, "&");
            for (String v : vs) {
                if (v != null && v.trim().length() > 0) {
                    queries.add(v);
                }
            }
        }
    }

    private static URL buildURL(String url) {
        URL u = null;
        try {
            u = new URL(url);
        } catch (java.net.MalformedURLException ex) {
            try {
                u = new URL("http://" + url);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }
        return u;
    }

    public URLParser addQuery(Map<String, Object> queries) {
        if (null != queries) {
            queries.forEach((k, v) -> addQuery(k, v));
        }
        return this;
    }

    public URLParser addQuery(String key, Object value) {
        queries.add(key + "=" + value.toString());
        return this;
    }

    public Map<String, String> getQueryPair() {
        Map<String, String> m = new HashMap<String, String>();
        for (String q : queries) {
            String[] vs = StringUtil.split(q, "=");
            if (vs.length == 1) {
                m.put(vs[0], "");
            } else {
                m.put(vs[0], vs[1]);
            }
        }
        return m;
    }

    private List<String> buildEncodeQueries() {
        List<String> encodeQueries = new ArrayList<String>();
        for (String q : queries) {
            String[] vs = StringUtil.split(q, "=");
            if (vs.length == 1) {
                encodeQueries.add(vs[0] + "=");
            } else {
                encodeQueries.add(vs[0] + "=" + encode(vs[0], vs[1]));
            }
        }
        return encodeQueries;
    }

    private String encode(String name, String s) {
        try {
            return URLEncoder.encode(s, this.charset);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            this.logger.warn("Url query decode error, replace by null. name=" + name + ", value=" + s);
            return null;
        }
    }

    public List<String> getQueries() {
        return this.queries;
    }

    public String getFullUrl() {
        StringBuilder strb = new StringBuilder();
        strb.append(protocol).append("://").append(host);
        if (port != -1) {
            strb.append(":").append(port);
        }
        strb.append(path);
        List<String> encodeQueries = buildEncodeQueries();
        String qs = StringUtil.join(encodeQueries.toArray(new String[0]), "&");
        if (qs != null) {
            strb.append("?").append(qs);
        }
        return strb.toString();
    }

    public String getUri() {
        return this.url.getPath();
    }

    public String toString() {
        return this.getFullUrl();
    }
}
