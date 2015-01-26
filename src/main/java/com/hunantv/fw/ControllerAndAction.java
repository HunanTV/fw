package com.hunantv.fw;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hunantv.fw.exceptions.Http500;
import com.hunantv.fw.view.View;

public class ControllerAndAction {

	public Controller controller;
	public Method action;

	public ControllerAndAction() {
	}

	public ControllerAndAction(Controller controller) {
		this.controller = controller;
	}

	public ControllerAndAction(Method action) {
		this.action = action;
	}

	public ControllerAndAction(Controller controller, Method action) {
		this.controller = controller;
		this.action = action;
	}

	public View doAction(HttpServletRequest request, HttpServletResponse response) throws Http500 {
		controller.setRequest(request);
		controller.setResponse(response);
		try {
			return (View) action.invoke(controller);
		} catch (Exception e) {
			throw new Http500(e);
		}
	}
}
