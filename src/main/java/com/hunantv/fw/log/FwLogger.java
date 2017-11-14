package com.hunantv.fw.log;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;

import com.hunantv.fw.Application;

public class FwLogger {

	private Logger logger = null;
	private static LoggerContext lc;

	static {
		try {
			String logbackConfPath = Application.getInstance().getSysConf().getConfPath() + "logback.xml";
			lc = (LoggerContext) LoggerFactory.getILoggerFactory();

			JoranConfigurator configurator = new JoranConfigurator();
			configurator.setContext(lc);
			lc.reset();
			configurator.doConfigure(logbackConfPath);

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

	public FwLogger(Class<?> clazz) {
		logger = FwLogger.lc.getLogger(clazz);
	}

	public void debug(String msg) {
		logger.debug(buildFormat(null), msg);
	}

	public void debug(String format, Object... msgs) {
		logger.debug(buildFormat(format), msgs);
	}

	public void info(String msg) {
		logger.info(buildFormat(null), msg);
	}

	public void info(String format, Object... msgs) {
		logger.info(buildFormat(format), msgs);
	}

	public void warn(String msg) {
		logger.warn(buildFormat(null), msg);
	}

	public void warn(String format, Object... msgs) {
		logger.warn(buildFormat(format), msgs);
	}

	public void trace(String msg) {
		logger.trace(buildFormat(null), msg);
	}

	public void trace(String format, Object... msgs) {
		logger.trace(buildFormat(format), msgs);
	}

	public void error(String msg) {
		logger.error(buildFormat(null), msg);
	}

	public void error(String format, Object... msgs) {
		logger.error(buildFormat(format), msgs);
	}

	private String buildFormat(String format) {
		String id = LogData.instance().getId();
		if (format == null)
			return id + "| {}";
		else
			return id + "| " + format;
	}
}
