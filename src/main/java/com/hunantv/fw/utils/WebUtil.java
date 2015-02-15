package com.hunantv.fw.utils;

import javax.servlet.http.HttpServletRequest;

public class WebUtil {

	public static boolean isMultipart(HttpServletRequest request) {
		String contentType = request.getContentType();
		return contentType != null && contentType.startsWith("multipart/form-data");
	}
}
