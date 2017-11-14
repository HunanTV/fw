package com.hunantv.fw.tests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpUpgradeHandler;
import javax.servlet.http.Part;

import com.hunantv.fw.exceptions.NotImplementException;
import com.hunantv.fw.net.URLParser;
import com.hunantv.fw.utils.StringUtil;

public class FakeRequest implements HttpServletRequest {

	private Map<String, String> parameter = new HashMap<String, String>();
	private URLParser urlParser;
	private String method;
	private Map<String, String> headers = new HashMap<String, String>();

	public void setMethod(String method) {
		this.method = method;
	}

	public void setURLParser(URLParser urlParser) {
		this.urlParser = urlParser;
	}

	public void setParameter(Map<String, String> parameter) {
		this.parameter = parameter;
	}

	public void addParameter(String key, Object value) {
		this.parameter.put(key, value.toString());
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	@Override
	public Object getAttribute(String name) {
		throw new NotImplementException();
	}

	@Override
	public Enumeration<String> getAttributeNames() {
		throw new NotImplementException();
	}

	@Override
	public String getCharacterEncoding() {
		throw new NotImplementException();
	}

	@Override
	public void setCharacterEncoding(String env) throws UnsupportedEncodingException {
		throw new NotImplementException();

	}

	@Override
	public int getContentLength() {
		throw new NotImplementException();
	}

	@Override
	public long getContentLengthLong() {
		throw new NotImplementException();
	}

	@Override
	public String getContentType() {
		return "text/html; charset=utf-8";
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		throw new NotImplementException();
	}

	@Override
	public String getParameter(String name) {
		return this.parameter.get(name);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Enumeration<String> getParameterNames() {
		return new KeysetEnum(parameter);
	}

	@Override
	public String[] getParameterValues(String name) {
		Collection<String> values = parameter.values();
		int i = 0, l = values.size();
		String[] relt = new String[l];

		for (String v : this.parameter.values()) {
			relt[i] = v;
			i++;
		}
		return relt;
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		throw new NotImplementException();
	}

	@Override
	public String getProtocol() {
		throw new NotImplementException();
	}

	@Override
	public String getScheme() {
		throw new NotImplementException();
	}

	@Override
	public String getServerName() {
		throw new NotImplementException();
	}

	@Override
	public int getServerPort() {
		throw new NotImplementException();
	}

	@Override
	public BufferedReader getReader() throws IOException {
		throw new NotImplementException();
	}

	@Override
	public String getRemoteAddr() {
		throw new NotImplementException();
	}

	@Override
	public String getRemoteHost() {
		throw new NotImplementException();
	}

	@Override
	public void setAttribute(String name, Object o) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeAttribute(String name) {
		// TODO Auto-generated method stub

	}

	@Override
	public Locale getLocale() {
		throw new NotImplementException();
	}

	@Override
	public Enumeration<Locale> getLocales() {
		throw new NotImplementException();
	}

	@Override
	public boolean isSecure() {
		throw new NotImplementException();
	}

	@Override
	public RequestDispatcher getRequestDispatcher(String path) {
		throw new NotImplementException();
	}

	@Override
	public String getRealPath(String path) {
		throw new NotImplementException();
	}

	@Override
	public int getRemotePort() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getLocalName() {
		throw new NotImplementException();
	}

	@Override
	public String getLocalAddr() {
		throw new NotImplementException();
	}

	@Override
	public int getLocalPort() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ServletContext getServletContext() {
		throw new NotImplementException();
	}

	@Override
	public AsyncContext startAsync() throws IllegalStateException {
		throw new NotImplementException();
	}

	@Override
	public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) throws IllegalStateException {
		throw new NotImplementException();
	}

	@Override
	public boolean isAsyncStarted() {
		throw new NotImplementException();
	}

	@Override
	public boolean isAsyncSupported() {
		throw new NotImplementException();
	}

	@Override
	public AsyncContext getAsyncContext() {
		throw new NotImplementException();
	}

	@Override
	public DispatcherType getDispatcherType() {
		throw new NotImplementException();
	}

	@Override
	public String getAuthType() {
		throw new NotImplementException();
	}

	@Override
	public Cookie[] getCookies() {
		throw new NotImplementException();
	}

	@Override
	public long getDateHeader(String name) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getHeader(String name) {
		return this.headers == null ? "" : this.headers.get(name);
	}

	@Override
	public Enumeration<String> getHeaders(String name) {
		throw new NotImplementException();
	}

	@Override
	public Enumeration<String> getHeaderNames() {
		throw new NotImplementException();
	}

	@Override
	public int getIntHeader(String name) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getMethod() {
		return this.method;
	}

	@Override
	public String getPathInfo() {
		throw new NotImplementException();
	}

	@Override
	public String getPathTranslated() {
		throw new NotImplementException();
	}

	@Override
	public String getContextPath() {
		throw new NotImplementException();
	}

	@Override
	public String getQueryString() {
		List<String> queryStringList = new ArrayList<String>();
		parameter.forEach((k, v) -> queryStringList.add(k + "=" + v));
		return StringUtil.join(queryStringList.toArray(new String[0]), "&");
	}

	@Override
	public String getRemoteUser() {
		throw new NotImplementException();
	}

	@Override
	public boolean isUserInRole(String role) {
		throw new NotImplementException();
	}

	@Override
	public Principal getUserPrincipal() {
		throw new NotImplementException();
	}

	@Override
	public String getRequestedSessionId() {
		throw new NotImplementException();
	}

	@Override
	public String getRequestURI() {
		return this.urlParser.getUri();
	}

	@Override
	public StringBuffer getRequestURL() {
		return new StringBuffer(this.urlParser.getFullUrl());
	}

	@Override
	public String getServletPath() {
		throw new NotImplementException();
	}

	@Override
	public HttpSession getSession(boolean create) {
		throw new NotImplementException();
	}

	@Override
	public HttpSession getSession() {
		throw new NotImplementException();
	}

	@Override
	public String changeSessionId() {
		throw new NotImplementException();
	}

	@Override
	public boolean isRequestedSessionIdValid() {
		throw new NotImplementException();
	}

	@Override
	public boolean isRequestedSessionIdFromCookie() {
		throw new NotImplementException();
	}

	@Override
	public boolean isRequestedSessionIdFromURL() {
		throw new NotImplementException();
	}

	@Override
	public boolean isRequestedSessionIdFromUrl() {
		throw new NotImplementException();
	}

	@Override
	public boolean authenticate(HttpServletResponse response) throws IOException, ServletException {
		throw new NotImplementException();
	}

	@Override
	public void login(String username, String password) throws ServletException {
		throw new NotImplementException();
	}

	@Override
	public void logout() throws ServletException {
		throw new NotImplementException();

	}

	@Override
	public Collection<Part> getParts() throws IOException, ServletException {
		throw new NotImplementException();
	}

	@Override
	public Part getPart(String name) throws IOException, ServletException {
		throw new NotImplementException();
	}

	@Override
	public <T extends HttpUpgradeHandler> T upgrade(Class<T> handlerClass) throws IOException, ServletException {
		throw new NotImplementException();
	}

}
