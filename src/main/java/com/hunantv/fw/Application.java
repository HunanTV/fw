package com.hunantv.fw;

import java.util.Map;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;

public class Application {

    private static Application instance = null;
    private Map settings;
    private int port;
    private Routes routes;
    private Server server;

    public static Application getInstance() {
	if (null == instance) {
	    instance = new Application();
	}
	return instance;
    }

    private Application() {
    }

    public Map getSettings() {
	return settings;
    }

    public void setSettings(Map settings) {
	this.settings = settings;
    }

    public Routes getRoutes() {
	return routes;
    }

    public void setRoutes(Routes routes) {
	this.routes = routes;
    }

    public int getPort() {
	return port;
    }

    public void listener(int port) {
	this.port = port;
	this.server = new Server(port);
    }

    public void start() throws Exception {
	WebAppContext webAppCtx = new WebAppContext();

	webAppCtx.setContextPath("/");
	webAppCtx.addServlet(new ServletHolder(new Dispatcher()), "/*");
	// String projectPath = new
	// String("/Users/ryan/Workspace/happy_sunshine");
	// webAppCtx.setDefaultsDescriptor(projectPath +
	// "/demo-war/target/jetty-demo-war/WEB-INF/web.xml");
	// webAppCtx.setResourceBase(projectPath +
	// "/demo-war/target/jetty-demo-war");
	webAppCtx.setDefaultsDescriptor("");
	webAppCtx.setResourceBase("");
	webAppCtx.setParentLoaderPriority(true);
	this.server.setHandler(webAppCtx);
	this.server.start();
    }

    public void stop() throws Exception {
	this.server.stop();
    }
}
