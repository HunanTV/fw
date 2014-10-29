package com.hunantv.fw.route;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by thomas on 14-10-29.
 */
public class URIMatcher {
    private static final Class[] EMPTY = new Class[0];


    private Pattern pattern;
    private List<NamedParam> pathParams = null;

    public List<NamedParam> getPathParams() {
        return pathParams;
    }

    public void setPathParams(List<NamedParam> pathParams) {
        this.pathParams = pathParams;
    }

    public void addPathParams(String name, Class type) {
        if (pathParams == null) {
            pathParams = new ArrayList<>(2);
        }

        pathParams.add(new NamedParam(name, type));
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    public Class[] getParameterTypeList() {
        if (this.pathParams == null) {
            return EMPTY;
        }

        return this.pathParams
                    .stream()
                    .map(param -> param.clazz)
                    .collect(Collectors.toList())
                    .toArray(new Class[0]);
    }

    public Collection<NamedValue> matches(String uri) {
        Matcher m = this.pattern.matcher(uri);
        if (!m.matches()) {
            return null;
        }

        if (this.pathParams == null) {
            return Collections.EMPTY_LIST;
        }

        return pathParams.stream().map( param -> {
            Object value = m.group(param.name);
            if (param.clazz == Integer.TYPE) {
                value = Integer.parseInt(m.group(param.name));
            }

            return new NamedValue(param.name, param.clazz, value);
        }).collect(Collectors.toList());
    }

    static class NamedParam {
        public final Class<?> clazz;
        public final String name;

        public NamedParam(String name, Class type) {
            this.clazz = type;
            this.name = name;
        }
    }

    static class NamedValue extends NamedParam {
        public final Object value;

        public NamedValue(String name, Class type, Object value) {
            super(name, type);
            this.value = value;
        }
    }
}
