package com.hunantv.fw.model;

public class Util {

	public static String getShortClassName(Class<?> c) {
		if (c == null)
			return "";

		String className = c.getName();
		int lastDot = className.lastIndexOf('.');
		if (lastDot != -1)
			className = className.substring(lastDot + 1);

		return className;
	}
}
