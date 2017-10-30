package com.hunantv.fw.fmext;

import java.util.Map;

import com.hunantv.fw.fmext.OverrideDirective.TemplateDirectiveBodyOverrideWraper;

import freemarker.core.Environment;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModelException;

public class DirectiveUtils {

	public static String BLOCK = "__ftl_override__";
	public static String OVERRIDE_CURRENT_NODE = "__ftl_override_current_node";

	public static String getOverrideVariableName(String name) {
		return BLOCK + name;
	}

	public static void exposeRapidMacros(Configuration conf) {
		conf.setSharedVariable(BlockDirective.DIRECTIVE_NAME, new BlockDirective());
		conf.setSharedVariable(ExtendsDirective.DIRECTIVE_NAME, new ExtendsDirective());
		conf.setSharedVariable(OverrideDirective.DIRECTIVE_NAME, new OverrideDirective());
		conf.setSharedVariable(SuperDirective.DIRECTIVE_NAME, new SuperDirective());
	}

	@SuppressWarnings("rawtypes")
    static String getRequiredParam(Map params, String key) throws TemplateException {
		Object value = params.get(key);
		if (value == null || value.toString().trim().length() == 0) {
			throw new TemplateModelException("not found required parameter:" + key + " for directive");
		}
		return value.toString();
	}

	@SuppressWarnings("rawtypes")
    static String getParam(Map params, String key, String defaultValue) throws TemplateException {
		Object value = params.get(key);
		return value == null ? defaultValue : value.toString();
	}

	static TemplateDirectiveBodyOverrideWraper getOverrideBody(Environment env, String name)
	        throws TemplateModelException {
		TemplateDirectiveBodyOverrideWraper value = (TemplateDirectiveBodyOverrideWraper) env
		        .getVariable(DirectiveUtils.getOverrideVariableName(name));
		return value;
	}

	static void setTopBodyForParentBody(Environment env, TemplateDirectiveBodyOverrideWraper topBody,
	        TemplateDirectiveBodyOverrideWraper overrideBody) {
		TemplateDirectiveBodyOverrideWraper parent = overrideBody;
		while (parent.parentBody != null) {
			parent = parent.parentBody;
		}
		parent.parentBody = topBody;
	}
}
