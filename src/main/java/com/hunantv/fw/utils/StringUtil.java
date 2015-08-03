package com.hunantv.fw.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	/**
	 * 针对中文字符转码
	 * 
	 * @param inputStr
	 * @return
	 */
	public String toUnicodeString(String inputStr) {
		StringBuffer strb = new StringBuffer();
		for (int i = 0, length = inputStr.length(); i < length; i++) {
			int c = inputStr.codePointAt(i);
			if (c > 0x4e00 && c <= 0x9fa5) {
				strb.append("\\u").append(Integer.toHexString(c));
			} else {
				strb.append(inputStr.charAt(i));
			}
		}
		return strb.toString();
	}

	public static String toMd5Str(String s) {
		if (s == null)
			return null;

		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			return null;
		}

		return toHex(md.digest(s.getBytes()));
	}

	private static String toHex(byte buffer[]) {
		StringBuffer sb = new StringBuffer(32);
		String s = null;
		for (int i = 0; i < buffer.length; i++) {
			s = Integer.toHexString((int) buffer[i] & 0xff);
			if (s.length() < 2)
				sb.append('0');
			sb.append(s);
		}
		return sb.toString();
	}

	public static String[] split(String str, String s) {
		if (str == null)
			return null;

		if (s == null)
			return new String[] { str };

		StringTokenizer st = new StringTokenizer(str, s);
		String[] r = new String[st.countTokens()];
		int i = 0;
		while (st.hasMoreTokens())
			r[i++] = st.nextToken();
		return r;
	}

	public static int indexOfIgnoreCase(String str, char ch) {
		return indexOfIgnoreCase(str, 0, ch);
	}

	public static int indexOfIgnoreCase(String str, int fromIndex, char ch) {
		if (str == null || str.length() == 0)
			return -1;

		if (fromIndex >= str.length())
			return -1; // Note: fromIndex might be near -1>>>1.

		if (fromIndex < 0)
			fromIndex = 0;

		for (int i = fromIndex; i < str.length(); i++) {
			if (StringUtil.equalsIngoreCase(ch, str.charAt(i)))
				return i;
		}
		return -1;
	}

	public static int indexOfIgnoreCase(String str, String target) {
		return indexOfIgnoreCase(str, 0, target);
	}

	public static int indexOfIgnoreCase(String str, int fromIndex, String target) {
		if (str == null || str.length() == 0 || target == null || target.length() == 0)
			return -1;

		if (fromIndex >= str.length())
			return -1;

		if (fromIndex < 0)
			fromIndex = 0;

		char first = target.charAt(0);
		int max = str.length() - target.length();

		for (int i = fromIndex; i <= max; i++) {
			if (!equalsIngoreCase(str.charAt(i), first)) {
				while (++i <= max && !equalsIngoreCase(str.charAt(i), first)) {
				}
			}
			if (i <= max) {
				int j = i + 1;
				int end = j + target.length() - 1;
				for (int k = 1; j < end && equalsIngoreCase(str.charAt(j), target.charAt(k)); j++, k++) {
				}
				if (j == end) // Found whole string.
					return i;
			}
		}

		return -1;
	}

	/**
	 * 返回第一次匹配正则表达式的串
	 * 
	 * @param str
	 * @param regexStr
	 * @return
	 */
	public static String matchStrFirst(String str, String regexStr) {
		Pattern p = Pattern.compile(regexStr);
		Matcher m = p.matcher(str);
		
		if (!m.matches() || m.groupCount() == 0)
			return null;
		return m.group(1); 
	}

	/**
	 * 返回匹配正则表达式的串
	 * 
	 * @param str
	 * @param regexStr
	 * @return
	 */
	public static String[] matchStr(String str, String regexStr) {
		Pattern p = Pattern.compile(regexStr);
		Matcher m = p.matcher(str);
		List<String> lst = new ArrayList<String>();
		if (m.matches()) {
			for (int i = 1, j = m.groupCount(); i <= j; i++) {
				lst.add(m.group(i));
			}
		}
		if (lst.size() > 0)
			return lst.toArray(new String[0]);
		return null;
	}

	public static boolean equalsIngoreCase(char c1, char c2) {
		if (c1 == c2)
			return true;

		// If characters don't match but case may be ignored,
		// try converting both characters to uppercase.
		// If the results match, then return true
		char u1 = Character.toUpperCase(c1);
		char u2 = Character.toUpperCase(c2);
		if (u1 == u2)
			return true;
		// Unfortunately, conversion to uppercase does not work properly
		// for the Georgian alphabet, which has strange rules about case
		// conversion. So we need to make one last check before
		// exiting.
		return (Character.toLowerCase(u1) == Character.toLowerCase(u2));
	}

	public static String addMarkIgnoreCase(String str, char r, String beginMark, String endMark) {
		if (str == null || str.length() == 0)
			return str;
		StringBuffer strb = new StringBuffer(str.length());
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);

			if (equalsIngoreCase(c, r))
				strb.append(beginMark).append(c).append(endMark);
			else
				strb.append(c);
		}
		return strb.toString();
	}

	public static String addMarkIgnoreCase(String str, String r, String beginMark, String endMark) {
		if (str == null || str.length() == 0 || r == null || r.length() == 0)
			return str;

		int p = indexOfIgnoreCase(str, r); // 找到被取代串的位置
		if (p == -1)
			return str;

		int last = 0;
		StringBuffer strb = new StringBuffer(str.length() << 1); // 声明一个StringBuffer,
		// 长度是 参数1
		// 字符串的两倍

		while (p >= 0) {
			strb.append(str.substring(last, p));
			strb.append(beginMark);
			strb.append(str.substring(p, p + r.length()));
			strb.append(endMark);

			last = p + r.length();
			p = indexOfIgnoreCase(str, last, r);
		}
		return strb.append(str.substring(last)).toString();
	}

	/**
	 * replace r to t from str
	 * 
	 * @param str
	 * @param r
	 * @param t
	 * @return
	 */
	public static String replace(String str, char r, char t) {
		if (str == null)
			return str;
		StringBuffer strb = new StringBuffer(str.length());
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c == r)
				c = t;
			strb.append(c);
		}
		return strb.toString();
	}

	/**
	 * replace r to t from str
	 * 
	 * @param str
	 * @param r
	 * @param t
	 * @return
	 */
	public static String replace(String str, String r, String t) {
		if (str == null || r == null || t == null)
			return str;
		if (str.trim().length() == 0 || r.length() == 0)
			return str;
		int p = str.indexOf(r); // 找到被取代串的位置
		if (p == -1)
			return str;

		int last = 0;
		StringBuffer strb = new StringBuffer(str.length() << 1); // 声明一个StringBuffer,
		// 长度是 参数1
		// 字符串的两倍

		while (p >= 0) {
			strb.append(str.substring(last, p));
			if (t != null)
				strb.append(t);
			last = p + r.length();
			p = str.indexOf(r, last);
		}
		return strb.append(str.substring(last)).toString();
	}

	/**
	 * replace first r to t from str
	 * 
	 * @param str
	 * @param r
	 * @param t
	 * @return
	 */
	public static String replaceFirst(String str, String r, String t) {
		if (str == null || r == null || t == null)
			return str;
		if (str.trim().length() == 0 || r.length() == 0)
			return str;
		int p = str.indexOf(r); // 找到被取代串的位置
		if (p == -1)
			return str;

		int last = 0;
		StringBuffer strb = new StringBuffer(str.length() << 1); // 声明一个StringBuffer,
		// 长度是 参数1
		// 字符串的两倍

		if (p >= 0) {
			strb.append(str.substring(last, p));
			if (t != null)
				strb.append(t);
			last = p + r.length();
			p = str.indexOf(r, last);
		}
		return strb.append(str.substring(last)).toString();
	}

	public static boolean isEn(String target) {
		for (int i = 0; i < target.length(); i++) {
			if (!isLetter(target.charAt(i)))
				return false;
		}
		return true;
	}

	public static boolean isRegex(String str) {
		if ((str.indexOf('*')) != -1 || (str.indexOf('?')) != -1)
			return true;
		return false;
	}

	public static boolean isLetter(char ch) {
		if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z'))
			return true;
		return false;
	}

	public static String join(String[] strs, String s) {
		StringBuilder strb = new StringBuilder();
		for (int i = 0; i < strs.length - 1; i++) {
			if (i == strs.length - 1)
				break;
			strb.append(strs[i]).append(s);
		}
		if (strs.length > 0) {
			strb.append(strs[strs.length - 1]);
		}
		return strb.toString();
	}

	public static String ensureEndedWith(String str, String suffix) {
		int len = str.length();
		if (str == null || len == 0) {
			return suffix;
		} else if (!str.endsWith(suffix)) {
			return str + suffix;
		}
		return str;
	}

	public static Integer str2Integer(String value) {
		return str2Integer(value, null);
	}

	public static Integer str2Integer(String value, Integer defaultValue) {
		if (value != null) {
			try {
				value = value.trim();
				return Integer.valueOf(value);
			} catch (NumberFormatException ex) {
				return defaultValue;
			}
		}
		return defaultValue;
	}

	public static Long str2Long(String value) {
		return str2Long(value, null);
	}

	public static Long str2Long(String value, Long defaultValue) {
		if (value != null) {
			try {
				value = value.trim();
				return Long.valueOf(value);
			} catch (NumberFormatException ex) {
				return defaultValue;
			}
		}
		return defaultValue;
	}

	public static Float str2Float(String value) {
		return str2Float(value, null);
	}

	public static Float str2Float(String value, Float defaultValue) {
		if (value != null) {
			try {
				value = value.trim();
				return Float.valueOf(value);
			} catch (NumberFormatException ex) {
				return defaultValue;
			}
		}
		return defaultValue;
	}

	public static Double str2Double(String value) {
		return str2Double(value, null);
	}

	public static Double str2Double(String value, Double defaultValue) {
		if (value != null) {
			try {
				value = value.trim();
				return Double.valueOf(value);
			} catch (NumberFormatException ex) {
				return defaultValue;
			}
		}
		return defaultValue;
	}

	public static String[] str2Array(String value) {
		return str2Array(value, null);
	}

	public static String[] str2Array(String value, String[] defaultValue) {
		if (value != null && value.trim().length() > 0) {
			return split(value, ",");
		}
		return defaultValue;
	}

	public static List str2List(String value) {
		return str2List(value, null);
	}

	public static List str2List(String value, List defaultValue) {
		if (value != null) {
			return Arrays.asList(StringUtil.split(value, ","));
		}
		return defaultValue;
	}

	public static void main(String[] args) {
		// String s1 = "abc abc";
		// String s2 = "中国";
		// System.out.println(StringUtil.isEn(s1));
		// System.out.println(StringUtil.isEn(s2));
		// s1 = StringUtil.replace(s1, " ", "");
		// System.out.println(s1);
		// String str = "123women大家都。3";
		// char ch = 'W';
		// String t = "mEN";
		// System.out.println(StringUtil.indexOfIgnoreCase(str, 3, ch));
		// System.out.println(StringUtil.indexOfIgnoreCase(str, 0, t));
		// System.out.println(StringUtil.addMarkIgnoreCase(str, ch, "<div>",
		// "</div>"));
		// System.out.println(StringUtil.addMarkIgnoreCase(str, t, "<div>",
		// "</div>"));

		// String str = "/save/中,文，-/29/中文,b,c,d,e/";
		// String r =
		// "^/save/([\\pP\\w\u4E00-\u9FA5]+)/(\\d+)/([\\w\u4E00-\u9FA5]+(,[\\w\u4E00-\u9FA5]+)*)/$";

		// String str = "/save/中,文，-/29/";
		// String r = "^/save/([\\pPa-zA-Z0-9\u4E00-\u9FA5]+)/(\\d+)/$";

		String str = "/save/abc/29/a,b,c,d,e,f";
		String r = "/save/(\\w+)/(\\d+)/(\\w+(?:,[\\w]+)*)";

		Pattern p = Pattern.compile(r);
		Matcher m = p.matcher(str);
		if (m.matches()) {
			for (int i = 0, j = m.groupCount(); i <= j; i++) {
				System.out.println(m.group(i));
			}
		}

		// for (String s : StringUtil.matchStr(str, r)) {
		// System.out.println(s);
		// }
		//
		// Pattern uriP =
		// Pattern.compile("<([a-zA-Z_][a-zA-Z_0-9]*)(:[^>]*)?>");
		// Matcher uriM =
		// uriP.matcher("/save/<name>/<int:age>/<list:types>/suffix");
		// System.out.println(uriM.find());
		// System.out.println(uriM.groupCount());
	}
}
