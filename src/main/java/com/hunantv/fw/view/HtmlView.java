package com.hunantv.fw.view;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import com.hunantv.fw.Application;
import com.hunantv.fw.exceptions.NotImplementException;

import freemarker.template.Template;
import freemarker.template.TemplateException;

public class HtmlView implements View {

	private String htmlPath;
	private Map<String, Object> data;

	public HtmlView(String htmlPath, Map<String, Object> data) {
		this.htmlPath = htmlPath;
		this.data = data;
	}

	@Override
	public String render() {
		throw new RuntimeException(new NotImplementException());
	}

	@Override
	public void renderTo(Writer writer) {
		Template tmpl;
		try {
			tmpl = Application.getInstance().getFreeMarkerCfg().getTemplate(htmlPath);
			tmpl.process(this.data, writer);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Object getV() {
		return null;
	}

	public String getHtmlPath() {
		return htmlPath;
	}

	public Map<String, Object> getData() {
		return data;
	}
}
