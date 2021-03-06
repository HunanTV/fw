package com.hunantv.fw;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hunantv.fw.utils.ByteBuffer;
import com.hunantv.fw.utils.StringUtil;
import com.hunantv.fw.utils.WebUtil;
import com.hunantv.fw.utils.XssShieldUtil;
import com.hunantv.fw.view.HtmlView;
import com.hunantv.fw.view.JsonPView;
import com.hunantv.fw.view.JsonView;
import com.hunantv.fw.view.RedirectView;
import com.hunantv.fw.view.StringView;
import com.hunantv.fw.view.View;

public class Controller {

	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected Map<String, String> partParams = new HashMap<String, String>();
	protected Map<String, RequestFile> files = new HashMap<String, RequestFile>();

	public String bodyString;
	private JSONObject jsonBodyObject;

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public View renderHtml(String htmlPath) {
		return new HtmlView(htmlPath, new HashMap<String, Object>());
	}

	public View renderHtml(String htmlPath, Map<String, Object> data) {
		return new HtmlView(htmlPath, data);
	}

	public View renderString(String str) {
		return new StringView(str);
	}

	private void addJsonContentType() {
		if (this.response != null) {
			this.response.setContentType("application/json;charset=UTF-8");
		}
	}

	public View renderJson(Object object) {
		return this.renderJson(Result.OK, "", object);
	}

	public View renderJson(int code, String msg) {
		return this.renderJson(code, msg, null);
	}

	public View renderJson(int code, String msg, Object object) {
		this.addJsonContentType();
		return new JsonView(new Result(code, msg, object));
	}

	public View renderJsonP(Object object) {
		return this.renderJsonP(Result.OK, "", object);
	}

	public View renderJsonP(int code, String msg) {
		return this.renderJsonP(code, msg, null);
	}

	public View renderJsonP(int code, String msg, Object object) {
		this.addJsonContentType();
		String callback = this.getStrNormalParam("callback", "JQuery_").trim();
		return new JsonPView(callback, new Result(code, msg, object));
	}

	public View renderJsonOrJsonP(Object object) {
		return this.renderJsonOrJsonP(Result.OK, "", object); // jsonp
	}

	public View renderJsonOrJsonP(int code, String msg) {
		return this.renderJsonOrJsonP(code, msg, null);
	}

	public View renderJsonOrJsonP(int code, String msg, Object object) {
		this.addJsonContentType();
		String callback = this.getStrNormalParam("callback", "").trim();
		callback = XssShieldUtil.stripXss(callback);
		Result relt = new Result(code, msg, object);
		if (callback.length() == 0) // json
			return new JsonView(relt);
		return new JsonPView(callback, relt); // jsonp
	}

	public View redirect(String str) {
		return new RedirectView(str);
	}

	// ////////////////////////////////////////////
	// get string params //////////////////////////
	// ////////////////////////////////////////////
	public String getStrParam(String name) {
		return getStrParam(name, null);
	}

	public String getStrParam(String name, String defaultValue) {
		if (WebUtil.isMultipart(request)) {
			String value = this.getStrPartParam(name);
			if (null != value)
				return value;
		}
		return this.getStrNormalParam(name, defaultValue);
	}

	public String getStrNormalParam(String name) {
		return this.getStrNormalParam(name, null);
	}

	public String getStrNormalParam(String name, String defaultValue) {
		String value = this.request.getParameter(name);
		return value == null ? defaultValue : value;
	}

	public String getStrPartParam(String name) {
		return getStrPartParam(name, null);
	}

	public String getStrPartParam(String name, String defaultValue) {
		String value = this.getPartParam(name);
		return value == null ? defaultValue : value;
	}

	public String getStrBodyParam(String name) {
		return this.getStrBodyParam(name, null);
	}

	public String getStrBodyParam(String name, String defaultValue) {
		String value = this.bodyJsonObj().getString(name);
		return value == null ? defaultValue : value;
	}

	// ////////////////////////////////////////////
	// get Integer params /////////////////////////
	// ////////////////////////////////////////////
	public Integer getIntegerParam(String name) {
		return getIntegerParam(name, null);
	}

	public Integer getIntegerParam(String name, Integer defaultValue) {
		if (WebUtil.isMultipart(request)) {
			Integer value = this.getIntegerPartParam(name);
			if (null != value)
				return value;
		}
		return this.getIntegerNormalParam(name, defaultValue);
	}

	public Integer getIntegerNormalParam(String name) {
		return getIntegerNormalParam(name, null);
	}

	public Integer getIntegerNormalParam(String name, Integer defaultValue) {
		String value = this.request.getParameter(name);
		return StringUtil.str2Integer(value, defaultValue);
	}

	public Integer getIntegerPartParam(String name) {
		return getIntegerPartParam(name, null);
	}

	public Integer getIntegerPartParam(String name, Integer defaultValue) {
		String value = getPartParam(name);
		return StringUtil.str2Integer(value);
	}

	public Integer getIntegerBodyParam(String name) {
		return this.getIntegerBodyParam(name, null);
	}

	public Integer getIntegerBodyParam(String name, Integer defaultValue) {
		Integer value = this.bodyJsonObj().getInteger(name);
		return value == null ? defaultValue : value;
	}

	// ////////////////////////////////////////////
	// get Long params ////////////////////////////
	// ////////////////////////////////////////////
	public Long getLongParam(String name) {
		return getLongParam(name, null);
	}

	public Long getLongParam(String name, Long defaultValue) {
		if (WebUtil.isMultipart(request)) {
			Long value = this.getLongPartParam(name);
			if (null != value)
				return value;
		}
		return this.getLongNormalParam(name, defaultValue);
	}

	public Long getLongNormalParam(String name) {
		return getLongNormalParam(name, null);
	}

	public Long getLongNormalParam(String name, Long defaultValue) {
		String value = this.request.getParameter(name);
		return StringUtil.str2Long(value, defaultValue);
	}

	public Long getLongPartParam(String name) {
		return getLongPartParam(name, null);
	}

	public Long getLongPartParam(String name, Integer defaultValue) {
		String value = getPartParam(name);
		return StringUtil.str2Long(value);
	}

	public Long getLongBodyParam(String name) {
		return this.getLongBodyParam(name, null);
	}

	public Long getLongBodyParam(String name, Long defaultValue) {
		Long value = this.bodyJsonObj().getLong(name);
		return value == null ? defaultValue : value;
	}

	// ////////////////////////////////////////////
	// get Float params ///////////////////////////
	// ////////////////////////////////////////////
	public Float getFloatParam(String name) {
		return getFloatParam(name, null);
	}

	public Float getFloatParam(String name, Float defaultValue) {
		if (WebUtil.isMultipart(request)) {
			Float value = this.getFloatPartParam(name);
			if (null != value)
				return value;
		}
		return this.getFloatNormalParam(name, defaultValue);
	}

	public Float getFloatNormalParam(String name) {
		return getFloatNormalParam(name, null);
	}

	public Float getFloatNormalParam(String name, Float defaultValue) {
		String value = this.request.getParameter(name);
		return StringUtil.str2Float(value, defaultValue);
	}

	public Float getFloatPartParam(String name) {
		return getFloatPartParam(name, null);
	}

	public Float getFloatPartParam(String name, Float defaultValue) {
		String value = getPartParam(name);
		return StringUtil.str2Float(value);
	}

	public Float getFloatBodyParam(String name) {
		return this.getFloatBodyParam(name, null);
	}

	public Float getFloatBodyParam(String name, Float defaultValue) {
		Float value = this.bodyJsonObj().getFloat(name);
		return value == null ? defaultValue : value;
	}

	// ////////////////////////////////////////////
	// get Double params //////////////////////////
	// ////////////////////////////////////////////
	public Double getDoubleParam(String name) {
		return getDoubleParam(name, null);
	}

	public Double getDoubleParam(String name, Double defaultValue) {
		if (WebUtil.isMultipart(request)) {
			Double value = this.getDoublePartParam(name);
			if (null != value)
				return value;
		}
		return this.getDoubleNormalParam(name, defaultValue);
	}

	public Double getDoubleNormalParam(String name) {
		return getDoubleNormalParam(name, null);
	}

	public Double getDoubleNormalParam(String name, Double defaultValue) {
		String value = this.request.getParameter(name);
		return StringUtil.str2Double(value, defaultValue);
	}

	public Double getDoublePartParam(String name) {
		return getDoublePartParam(name, null);
	}

	public Double getDoublePartParam(String name, Double defaultValue) {
		String value = getPartParam(name);
		return StringUtil.str2Double(value);
	}

	public Double getDoubleBodyParam(String name) {
		return this.getDoubleBodyParam(name, null);
	}

	public Double getDoubleBodyParam(String name, Double defaultValue) {
		Double value = this.bodyJsonObj().getDouble(name);
		return value == null ? defaultValue : value;
	}

	// ////////////////////////////////////////////
	// get List params ////////////////////////////
	// ////////////////////////////////////////////
	public List<String> getListParam(String name) {
		return getListParam(name, null);
	}

	public List<String> getListParam(String name, List<String> defaultValue) {
		if (WebUtil.isMultipart(request)) {
			List<String> value = this.getListPartParam(name);
			if (null != value)
				return value;
		}
		return this.getListNormalParam(name, defaultValue);
	}

	public List<String> getListNormalParam(String name) {
		return this.getListNormalParam(name, null);
	}

	public List<String> getListNormalParam(String name, List<String> defaultValue) {
		String value = this.request.getParameter(name);
		return StringUtil.str2List(value, defaultValue);
	}

	public List<String> getListPartParam(String name) {
		return this.getListPartParam(name, null);
	}

	public List<String> getListPartParam(String name, List<String> defaultValue) {
		String value = this.getPartParam(name);
		return StringUtil.str2List(value, defaultValue);
	}

	public List getListBodyParam(String name) {
		return this.getListBodyParam(name, null);
	}

	public List getListBodyParam(String name, List defaultValue) {
		String text = this.bodyJsonObj().getString(name);
		if (text == null) {
			return defaultValue;
		}
		return JSON.parseArray(text);
	}

	// ////////////////////////////////////////////
	// get Array params ///////////////////////////
	// ////////////////////////////////////////////

	public String[] getArrayParam(String name) {
		return getArrayParam(name, null);
	}

	public String[] getArrayParam(String name, String[] defaultValue) {
		if (WebUtil.isMultipart(request)) {
			String[] value = this.getArrayPartParam(name);
			if (null != value)
				return value;
		}
		return this.getArrayNormalParam(name, defaultValue);
	}

	public String[] getArrayNormalParam(String name) {
		return this.getArrayNormalParam(name, null);
	}

	public String[] getArrayNormalParam(String name, String[] defaultValue) {
		String value = this.request.getParameter(name);
		return StringUtil.str2Array(value, defaultValue);
	}

	public String[] getArrayPartParam(String name) {
		return this.getArrayPartParam(name, null);
	}

	public String[] getArrayPartParam(String name, String[] defaultValue) {
		String value = this.getPartParam(name);
		return StringUtil.str2Array(value, defaultValue);
	}

	protected RequestFile getFile(String name) {
		try {
			if (!partParams.containsKey(name)) {
				Part part = this.request.getPart(name);
				if (part == null)
					return null;
				RequestFile f = new RequestFile();
				f.fieldName = part.getName();
				f.fileName = part.getSubmittedFileName();
				f.contentType = part.getContentType();

				InputStream in = part.getInputStream();
				ByteBuffer bb = new ByteBuffer();
				byte[] bytes = new byte[20 << 10];
				int len = -1;
				while (-1 != (len = in.read(bytes))) {
					bb.append(bytes, 0, len);
				}
				f.content = bb.usedArray();
				f.size = f.content.length;
				
				files.put(f.fieldName, f);
			}
			return files.get(name);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	protected String getPartParam(String name) {
		try {
			if (!partParams.containsKey(name)) {
				Part part = this.request.getPart(name);
				if (part == null)
					return null;
				InputStream in = part.getInputStream();
				StringBuilder strb = new StringBuilder();
				byte[] bytes = new byte[20 << 10];
				int len = -1;
				while (-1 != (len = in.read(bytes))) {
					strb.append(new String(bytes, 0, len));
				}
				partParams.put(name, strb.toString());
			}
			return partParams.get(name);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public String body() {
		if (null == bodyString) {
			bodyString = getBody();
		}
		return bodyString;
	}

	public JSONObject bodyJsonObj() {
		if (null == jsonBodyObject) {
			jsonBodyObject = JSONObject.parseObject(body());
		}
		return jsonBodyObject;
	}

	protected String getBody() {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream(), "UTF-8"));
			StringBuffer sb = new StringBuffer("");
			String temp;
			while ((temp = br.readLine()) != null) {
				sb.append(temp);
			}
			return sb.toString();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			try {
				br.close();
			} catch (Exception ex) {

			} finally {
				br = null;
			}
		}
	}
}
