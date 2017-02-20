package com.hunantv.fw.utils;

import javax.servlet.http.HttpServletRequest;

public class WebUtil {

	public static boolean isMultipart(HttpServletRequest request) {
		String contentType = request.getContentType();
		return contentType != null && contentType.startsWith("multipart/form-data");
	}
	public static boolean isTextHtml(HttpServletRequest request) {
		String HeaderValue = request.getHeader("Accept");
		return  HeaderValue != null && HeaderValue.startsWith("text/html");
	}
	private static String[] IP_HEADS = new String[] { 
		"X-Forwarded-For", 
		"Proxy-Client-IP", 
		"WL-Proxy-Client-IP",
	    "HTTP_CLIENT_IP", 
	    "HTTP_X_FORWARDED_FOR",
	};

	public static String getRemoteAddr(HttpServletRequest request) {
		for (String header : IP_HEADS) {
			String ip = request.getHeader(header);
			if (ip != null && ip.length() > 0 && !"unknown".equalsIgnoreCase(ip))
				return ip;
		}
		return request.getRemoteAddr();
	}
}
