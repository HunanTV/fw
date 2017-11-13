package com.hunantv.fw.log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.hunantv.fw.Application;
import com.hunantv.fw.utils.SeqID;
import com.hunantv.fw.utils.SysConf;

public class FwLogger {

	private Logger logger = null;

	static {
		try {
			SysConf sysConf = Application.getInstance().getSysConf();
			PropertyConfigurator.configure(sysConf.getConfPath() + "log4j.properties");
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public FwLogger delayInfo(String key, Object msg) {
		if (msg == null) {
			msg = "";
		}
		LogData.instance().add(key, msg.toString());
		return this;
	}

	public FwLogger clear() {
		this.info(LogData.instance().toString());
		LogData.instance().clear();
		return this;
	}

	public static FwLogger getLogger(Class<?> clz) {
		return new FwLogger(clz);
	}

	public static FwLogger getLogger(String clz) {
		return new FwLogger(clz);
	}

	public FwLogger(Class<?> clazz) {
		this(clazz.getName());
	}

	public FwLogger(String clazz) {
		logger = Logger.getLogger(clazz);
	}

	public void debug(Object message) {
		if (logger.isDebugEnabled())
			logger.debug(buildMsg(message));
	}

	public void debug(Object message, Throwable t) {
		if (logger.isDebugEnabled())
			logger.debug(buildMsg(message), t);
	}

	public void info(Object message) {
		if (logger.isInfoEnabled())
			logger.info(buildMsg(message));
	}

	public void info(Object message, Throwable t) {
		if (logger.isInfoEnabled())
			logger.info(buildMsg(message), t);
	}

	public void warn(Object message) {
		if (logger.isEnabledFor(Level.WARN))
			logger.warn(buildMsg(message));
	}

	public void warn(Object message, Throwable t) {
		if (logger.isEnabledFor(Level.WARN))
			logger.warn(buildMsg(message), t);
	}

	public void error(Object message) {
		if (logger.isEnabledFor(Level.ERROR))
			logger.error(buildMsg(message));
	}

	public void error(Object message, Throwable t) {
		if (logger.isEnabledFor(Level.ERROR))
			logger.error(buildMsg(message), t);
	}

	public void fatal(Object message) {
		if (logger.isEnabledFor(Level.FATAL))
			logger.fatal(buildMsg(message));
	}

	public void fatal(Object message, Throwable t) {
		if (logger.isEnabledFor(Level.FATAL))
			logger.fatal(buildMsg(message), t);
	}

	public void trace(Object message) {
		if (logger.isTraceEnabled())
			logger.trace(buildMsg(message));
	}

	public void trace(Object message, Throwable t) {
		if (logger.isTraceEnabled())
			logger.trace(buildMsg(message), t);
	}

	private String buildMsg(Object message) {
		if (message == null) {
			return LogData.instance().getId() + "|";
		}
		return LogData.instance().getId() + "|" + message.toString();
	}
}