package com.hunantv.fw;

import com.hunantv.fw.route.Route;
import com.hunantv.fw.route.Routes;
import com.hunantv.fw.utils.SysConf;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.PropertyConfigurator;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.util.component.LifeCycle;
import org.eclipse.jetty.util.component.LifeCycle.Listener;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class Application {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static Application instance = new Application();
    private boolean debug = false;
    private Map<String, ?> settings;
    private int port;
    private Routes routes;
    private Server server;
    private ServletHandler handler;
    private SysConf sysConf;
    private Properties jettyPros;
    private final ConcurrentHashMap<ServerLifeCycleListener, LifeCycle.Listener> _listeners = new ConcurrentHashMap<>();

    public static Application getInstance() {
        return instance;
    }

    private Application() {
        sysConf = new SysConf();
        try {
            PropertyConfigurator.configure(sysConf.getConfPath() + "logback.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public AppInfo getAppInfo() {
        return AppInfo.getInfo();
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
        this.routes.add(Route.get("/app/version", VersionController.class, "version"));
    }

    public int getPort() {
        return port;
    }

    public SysConf getSysConf() {
        return sysConf;
    }

    private Map<String, Object> filterProperties(Properties pros, String prefix) {
        Map<String, Object> map = new HashMap<>();
        pros.forEach((k, v) -> {
            String key = (String)k;
            if (key.startsWith(prefix)) {
                String subKey = key.substring(prefix.length() + 1);
                map.put(subKey, v);
                logger.debug("load properties: {} = {}", subKey, v);
            }
            map.put(key, pros.get(key));
        });
        return map;
    }

    public void listener(int port) {
        initJettyConfig();

        this.port = port;
        HttpConfiguration httpConfiguration = initHttpConfiguration();
        HttpConnectionFactory httpConnectionFactory = new HttpConnectionFactory(httpConfiguration);

        QueuedThreadPool threadPool = this.initThreadPool();
        this.server = new Server(threadPool);
        int cores = Runtime.getRuntime().availableProcessors();
        ServerConnector connector = new ServerConnector(server, null, null, null, 1 + cores / 2, -1,
                httpConnectionFactory);
        initConnector(connector);
        connector.setPort(this.port);
        server.setConnectors(new Connector[] { connector });
        logger.info("Application listen on " + port);
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
            logger.info("Init jetty config ok");
        } catch (Exception ex) {
            logger.warn("Init jetty config failed", ex);
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

    public void start() throws Exception {
        this.server.setHandler(new Dispatcher());
        this.server.start();
        this.server.join();
    }

    public void addFilterWithMapping(Class<? extends Filter> filter, String pathSpec, int dispatches) {
        this.handler.addFilterWithMapping(filter, pathSpec, dispatches);
    }

    public void addServerListener(ServerLifeCycleListener listener) {
        Listener lifeCycleListener = new Listener() {

            @Override
            public void lifeCycleStopping(LifeCycle event) {
                listener.stopping();
            }

            @Override
            public void lifeCycleStopped(LifeCycle event) {
                listener.stopped();
            }

            @Override
            public void lifeCycleStarting(LifeCycle event) {
                listener.starting();
            }

            @Override
            public void lifeCycleStarted(LifeCycle event) {
                listener.started();
            }

            @Override
            public void lifeCycleFailure(LifeCycle event, Throwable cause) {
                listener.failure(cause);
            }
        };
        _listeners.put(listener, lifeCycleListener);
        this.server.addLifeCycleListener(lifeCycleListener);
    }

    public void removeServerListener(ServerLifeCycleListener listener) {
        if (this._listeners.containsKey(listener)) {
            this.server.removeLifeCycleListener(this._listeners.get(listener));
        }
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
