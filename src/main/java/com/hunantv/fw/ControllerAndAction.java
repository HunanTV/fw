package com.hunantv.fw;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hunantv.fw.exceptions.HttpException;
import com.hunantv.fw.view.View;

public class ControllerAndAction {

	public Controller controller;
	public Method action;
	public Object[] args = null;
	
	public ControllerAndAction() {
	}

	public ControllerAndAction(Controller controller, Method action) {
		this.controller = controller;
		this.action = action;
	}
	
	public ControllerAndAction(Controller controller, Method action, Object[] args) {
		this.controller = controller;
		this.action = action;
		this.args = args;
	}

	public View doAction(HttpServletRequest request, HttpServletResponse response) throws HttpException {
		controller.setRequest(request);
		controller.setResponse(response);
		try {
			if (this.args == null) // 无参数
				return (View) action.invoke(controller);
			return (View) action.invoke(controller, args);
		} catch (Exception ex) {
			throw HttpException.err500(ex);
		}
	}
}
