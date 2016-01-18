package com.hunantv.fw.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.hunantv.fw.Application;

class LoggerData {
	public String id;
	private List<String> data;

	public LoggerData() {
		this(SeqID.rnd().toString());
	}

	public LoggerData(String id) {
		this.id = id;
		data = new ArrayList<String>();
		// this.add("SeqID", this.id);
	}

	public void add(String key, String value) {
		this.data.add(key + "=" + value);
	}

	public String toString() {
		if (data.size() == 0)
			return "";
		StringBuilder strb = new StringBuilder();
		strb.append("[ ");
		for (String ele : data) {
			strb.append(ele).append(" ");
		}
		strb.append("]");
		return strb.toString();
	}
}

public class FwLogger {

	private Logger logger = null;
	private static ThreadLocal<LoggerData> threadLocalVar = new ThreadLocal<LoggerData>();

	static {
		try {
			SysConf sysConf = Application.getInstance().getSysConf();
			PropertyConfigurator.configure(sysConf.getConfPath() + "log4j.properties");
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public FwLogger delayInfo(String key, Object msg) {
		initSeqid();
		LoggerData data = (LoggerData) threadLocalVar.get();
		data.add(key, msg.toString());
		return this;
	}

	public String getSeqid() {
		return this.initSeqid();
	}

	public String initSeqid() {
		LoggerData data = (LoggerData) threadLocalVar.get();
		if (data == null) {
			data = new LoggerData();
			FwLogger.threadLocalVar.set(data);
		}
		return data.id;
	}

	public void clearSeqid() {
		LoggerData data = (LoggerData) threadLocalVar.get();
		this.info(data.toString());
		FwLogger.threadLocalVar.remove();
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

	public void fatal(Object message) {
		if (logger.isEnabledFor(Level.FATAL))
			logger.fatal(buildMsg(message));
	}

	public void fatal(Object message, Throwable t) {
		if (logger.isEnabledFor(Level.FATAL))
			logger.fatal(buildMsg(message), t);
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

	public void trace(Object message) {
		if (logger.isTraceEnabled())
			logger.trace(buildMsg(message));
	}

	public void trace(Object message, Throwable t) {
		if (logger.isTraceEnabled())
			logger.trace(buildMsg(message), t);
	}

	private String buildMsg(Object message) {
	    if(message == null){
	        return this.getSeqid() + "|";
	    }
		return this.getSeqid() + "|" + message.toString();
	}
}
