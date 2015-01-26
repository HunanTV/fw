package com.hunantv.fw;

import java.lang.reflect.Method;

public class ControllerAndMethod {

	public Controller controller;
	public Method method;

	public ControllerAndMethod() {
	}

	public ControllerAndMethod(Controller controller) {
		this.controller = controller;
	}

	public ControllerAndMethod(Method method) {
		this.method = method;
	}

	public ControllerAndMethod(Controller controller, Method method) {
		this.controller = controller;
		this.method = method;
	}
}
