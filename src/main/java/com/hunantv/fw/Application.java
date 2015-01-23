package com.hunantv.fw;

import java.io.File;
import java.util.Locale;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hunantv.fw.route.Routes;
import com.hunantv.fw.utils.SysConf;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class Application {

	private static final Logger logger = Logger.getLogger(Application.class);

	private static Application instance = null;
	private boolean debug = false;
	private Map<String, ?> settings;
	private int port;

	private Routes routes;
	private Server server;
	private DataSource ds;
	private Configuration freeMarkerCfg;
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
		initLog4j();
		initFreemarker();
		initSpring();
	}

	private void initFreemarker() {
		try {
			freeMarkerCfg = new Configuration();
			freeMarkerCfg.setDirectoryForTemplateLoading(new File(sysConf.getSysPath() + "views"));
			freeMarkerCfg.setDefaultEncoding("UTF-8");
			logger.info("init freemarker ok");
		} catch (Exception ex) {
			logger.error("init freemarker failed", ex);
			throw new RuntimeException(ex);
		}
	}

	private void initLog4j() {
		try {
			PropertyConfigurator.configure(sysConf.getConfPath() + "log4j.properties");
			logger.info("init log4j ok");
		} catch (Exception ex) {
			logger.error("init log4j failed", ex);
			throw new RuntimeException(ex);
		}
	}

	private void initSpring() {
		try {
			String springXmlPath = sysConf.getConfUri() + "spring.xml";
			springCtx = new ClassPathXmlApplicationContext(springXmlPath);
			logger.info("init spring ok");
		} catch (Exception ex) {
			logger.error("init spring failed", ex);
			throw new RuntimeException(ex);
		}
	}

	public ClassPathXmlApplicationContext getSpringCtx() {
		return this.springCtx;
	}

	public Configuration getFreeMarkerCfg() {
		return this.freeMarkerCfg;
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
		// this.server = new Server(port);

		this.server = new Server();
		HttpConfiguration httpConfiguration = new HttpConfiguration();
		httpConfiguration.setOutputBufferSize(32768);
		httpConfiguration.setRequestHeaderSize(8192);
		httpConfiguration.setResponseHeaderSize(8192);
		httpConfiguration.setSendServerVersion(false);
		httpConfiguration.setHeaderCacheSize(512);
		ServerConnector connector = new ServerConnector(server, new HttpConnectionFactory(httpConfiguration));
		connector.setPort(this.port);
		server.setConnectors(new Connector[] { connector });
	}

	public void start() throws Exception {
		WebAppContext webAppCtx = new WebAppContext();
		webAppCtx.setContextPath("/");
		webAppCtx.addServlet(new ServletHolder(new Dispatcher()), "/*");
		/*
		 * String projectPath = new String("/Users/ryan/Workspace/happy_sunshine");
		 * webAppCtx.setDefaultsDescriptor(projectPath + "/demo-war/target/jetty-demo-war/WEB-INF/web.xml");
		 * webAppCtx.setResourceBase(projectPath + "/demo-war/target/jetty-demo-war");
		 */
		webAppCtx.setDefaultsDescriptor("");
		webAppCtx.setResourceBase("");
		webAppCtx.setParentLoaderPriority(true);
		this.server.setHandler(webAppCtx);
		logger.info("application listen on " + port);
		this.server.start();
		this.server.join();
	}

	public void stop() throws Exception {
		this.server.stop();
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public boolean isDebug() {
		return this.debug;
	}
}
