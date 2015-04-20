package com.hunantv.fw;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.PropertyConfigurator;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hunantv.fw.route.Routes;
import com.hunantv.fw.utils.FwLogger;
import com.hunantv.fw.utils.SysConf;

public class Application {
	
	private static final FwLogger logger = new FwLogger(Application.class);

	private static Application instance = null;
	private boolean debug = false;
	private Map<String, ?> settings;
	private int port;

	private Routes routes;
	private Server server;
	private SysConf sysConf;
	private Properties jettyPros;
//	private ClassPathXmlApplicationContext springCtx;
	
	
	public static Application getInstance() {
		if (null == instance) {
			instance = new Application();
		}
		return instance;
	}

	private Application() {
		sysConf = new SysConf();
//		initSpring();
	}

//	private void initSpring() {
//		try {
//			String springXmlPath = sysConf.getConfUri() + "spring.xml";
//			springCtx = new ClassPathXmlApplicationContext(springXmlPath);
//			logger.info("init spring ok");
//		} catch (Exception ex) {
//			logger.error("init spring failed", ex);
//			throw new RuntimeException(ex);
//		}
//	}
//	
//	public ClassPathXmlApplicationContext getSpringCtx() {
//		return this.springCtx;
//	}

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

	public SysConf getSysConf() {
		return sysConf;
	}
	
	private Map<String, Object> filterProperties(Properties pros, String prefix) {

		Map<String, Object> map = new HashMap<String, Object>();
		for (Iterator iter = pros.keySet().iterator(); iter.hasNext();) {
			String key = (String) iter.next();
			if (key.startsWith(prefix)) {
				System.out.println(key.substring(prefix.length()+1));
				map.put(key.substring(prefix.length()+1), pros.get(key));
			}
			map.put(key, pros.get(key));
		}
		return map;
	}
	
	public void listener(int port) {
		initJettyConfig();
		
		this.port = port;
		HttpConfiguration httpConfiguration = initHttpConfiguration();
		HttpConnectionFactory httpConnectionFactory = new HttpConnectionFactory(httpConfiguration);
		
		QueuedThreadPool threadPool = this.initThreadPool();
		this.server = new Server(threadPool);
		
//		ServerConnector connector = new ServerConnector(server, httpConnectionFactory);
		
		int cores = Runtime.getRuntime().availableProcessors();
		ServerConnector connector = new ServerConnector(server, null, null, null, 1+cores/2, -1, httpConnectionFactory);
		initConnector(connector);
		connector.setPort(this.port);
		
		server.setConnectors(new Connector[] { connector });
	}
	
	private ServerConnector initConnector(ServerConnector connector) {
		Map<String, Object> poolCfg = filterProperties(jettyPros, "jetty.connector");
		try {
	        BeanUtils.populate(connector, poolCfg);
        } catch (Exception ex) {
        	throw new RuntimeException(ex);
        }
		return connector;
    }

	private QueuedThreadPool initThreadPool() {
		QueuedThreadPool threadPool = new QueuedThreadPool();
		Map<String, Object> poolCfg = filterProperties(jettyPros, "jetty.threadpool");
		try {
	        BeanUtils.populate(threadPool, poolCfg);
        } catch (Exception ex) {
        	throw new RuntimeException(ex);
        }
		return threadPool;
	}
	
	private void initJettyConfig() {
		try {
			this.jettyPros = sysConf.read("jetty.properties");
			logger.info("init jetty ok");
		} catch (Exception ex) {
			logger.warn("init jetty failed", ex);
			throw new RuntimeException(ex);
		}
	}

	private HttpConfiguration initHttpConfiguration() {
		HttpConfiguration httpConfiguration = new HttpConfiguration();
		Map<String, Object> httpCfg = filterProperties(jettyPros, "jetty.http");
		try {
	        BeanUtils.populate(httpConfiguration, httpCfg);
        } catch (Exception ex) {
        	throw new RuntimeException(ex);
        }
		return httpConfiguration;
	}
	
//	public void start() throws Exception {
//		WebAppContext webAppCtx = new WebAppContext();
//		webAppCtx.setContextPath("/");
//		webAppCtx.addServlet(new ServletHolder(new Dispatcher()), "/*");
//		/*
//		 * String projectPath = new String("/Users/ryan/Workspace/happy_sunshine");
//		 * webAppCtx.setDefaultsDescriptor(projectPath + "/demo-war/target/jetty-demo-war/WEB-INF/web.xml");
//		 * webAppCtx.setResourceBase(projectPath + "/demo-war/target/jetty-demo-war");
//		 */
//		webAppCtx.setDefaultsDescriptor("");
//		webAppCtx.setResourceBase("");
//		webAppCtx.setParentLoaderPriority(true);
//		this.server.setHandler(webAppCtx);
//
//		logger.info("application listen on " + port);
//		this.server.start();
//		this.server.join();
//	}
	
	
	public void start() throws Exception {
	    ServletHandler handler = new ServletHandler();
	    server.setHandler(handler);
	    handler.addServletWithMapping(Dispatcher.class, "/*");

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
