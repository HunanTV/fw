package com.hunantv.fw.utils;

import org.apache.log4j.Logger;

public class FwLogger {

	private Logger logger = null;
	private static ThreadLocal threadLocalVar = new ThreadLocal();

	public String getSeqid() {
		return (String) FwLogger.threadLocalVar.get();
	}

	public void setSeqid(String seqid) {
		FwLogger.threadLocalVar.set(seqid);
	}

	public void clearSeqid() {
		FwLogger.threadLocalVar.remove();
	}

	public FwLogger(Class<?> clazz) {
		logger = Logger.getLogger(clazz);
	}

	public void debug(Object message) {
		logger.debug(buildMsg(message));
	}

	public void debug(Object message, Throwable t) {
		logger.debug(buildMsg(message), t);
	}

	public void info(Object message) {
		logger.info(buildMsg(message));
	}

	public void info(Object message, Throwable t) {
		logger.info(buildMsg(message), t);
	}

	public void fatal(Object message) {
		logger.fatal(buildMsg(message));
	}

	public void fatal(Object message, Throwable t) {
		logger.fatal(buildMsg(message), t);
	}

	public void warn(Object message) {
		logger.warn(buildMsg(message));
	}

	public void warn(Object message, Throwable t) {
		logger.warn(buildMsg(message), t);
	}

	public void error(Object message) {
		logger.error(buildMsg(message));
	}

	public void error(Object message, Throwable t) {
		logger.error(buildMsg(message), t);
	}

	public void trace(Object message) {
		logger.trace(buildMsg(message));
	}

	public void trace(Object message, Throwable t) {
		logger.trace(buildMsg(message), t);
	}

	private String buildMsg(Object message) {
		return this.getSeqid() + "|" + message.toString();
	}
}
