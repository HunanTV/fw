package com.hunantv.fw.route;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by thomas on 14-10-29.
 */
public class MongoURITemplateProcessor {
    private static Pattern pattern = Pattern.compile("<([^>]+)>");
    private static Pattern labelPattern = Pattern.compile(
            "(?:(?<type>[^:]+):)?(?<labelName>.+)");

    public static URIMatcher process(String uri) {
        Matcher m = pattern.matcher(uri);

        StringBuffer newPatternString = new StringBuffer(uri.length());

        URIMatcher uriMatcher = new URIMatcher();
        while (m.find()) {
            m.appendReplacement(newPatternString,
                                parseLabel(uriMatcher, m.group(1)));
        }

        m.appendTail(newPatternString);

        uriMatcher.setPattern(Pattern.compile(newPatternString.toString()));
        return uriMatcher;
    }

    static String parseLabel(URIMatcher uriMatcher, String label) {
        Matcher m = labelPattern.matcher(label);

        if (! m.matches()) {
            return "";
        }

        String type = m.group("type");
        String labelName = m.group("labelName");

        if (type == null) {
            type = "str"; // to string .
        }
        switch (type.toLowerCase()) {
            case "int":
            case "long":
                uriMatcher.addPathParams(labelName, Integer.TYPE);
                return acceptIntPattern(labelName);
            case "":
            case "string":
            case "str":
            default:
                uriMatcher.addPathParams(labelName, String.class);
                return acceptStringPattern(labelName);

        }
    }

    private static String acceptStringPattern(String labelName) {
        return "(?<" + labelName + ">[^/]+)";
    }

    static String acceptIntPattern(String labelName) {
        return "(?<" + labelName + ">\\\\d+)";
    }
}
