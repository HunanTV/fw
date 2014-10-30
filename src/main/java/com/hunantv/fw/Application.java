package com.hunantv.fw;

import java.util.Map;

import javax.sql.DataSource;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hunantv.fw.route.Routes;
import com.hunantv.fw.utils.SysConf;

public class Application {

	private static Application instance = null;
	private Map<String, ?> settings;
	private int port;

	private Routes routes;
	private Server server;
	private DataSource ds;
	private SysConf sysConf;

	private ClassPathXmlApplicationContext springCtx;

	public static Application getInstance() {
		if (null == instance) {
			instance = new Application();
		}
		return instance;
	}

	private Application() {
		sysConf = new SysConf();
		initSpring();
	}

	private void initSpring() {
		try {
			String springXmlPath = sysConf.getConfUri() + "spring.xml";
			springCtx = new ClassPathXmlApplicationContext(springXmlPath);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public ClassPathXmlApplicationContext getSpringCtx() {
		return this.springCtx;
	}

	public Map<String, ?> getSettings() {
		return settings;
	}

	public void setSettings(Map<String, ?> settings) {
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

	public DataSource getDs() {
		return ds;
	}

	public SysConf getSysConf() {
		return sysConf;
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
