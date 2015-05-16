package com.hunantv.fw.route;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UriParser {

	public static void main1() {
		String s = "/user/save/<int:age>/111/<str:name>/222/<password>";
		// String s = "/user/save";
		String r = "<([a-zA-Z_][a-zA-Z_0-9]*)(:[^>]*)?>";
		Pattern p = Pattern.compile(r);
		Matcher m = p.matcher(s);

		while (m.find()) {
			System.out.println(m.group());
		}
	}

	public static void main2() {
		String[] ss = new String[] { "<int:age>", "<str:name>", "<password>" };
		String r = "<(int:|str:|float:|list:)?(.*)>";

		for (String s : ss) {
			Pattern p = Pattern.compile(r);
			Matcher m = p.matcher(s);

			while (m.find()) {
				System.out.println(m.group(1) + "|" + m.group(2));
			}
			System.out.println("---");
		}
	}

	public static void main(String[] args) {
		main1();
		System.out.println("*********************");
		main2();
	}
}
