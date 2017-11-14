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

	public void debug(Object... msgs) {
		logger.debug(buildMarker(msgs), msgs);
	}

	public void info(Object... msgs) {
		logger.info(buildMarker(msgs), msgs);
	}

	public void warn(Object... msgs) {
		logger.warn(buildMarker(msgs), msgs);
	}

	public void error(Object... msgs) {
		logger.error(buildMarker(msgs), msgs);
	}

	public void trace(Object... msgs) {
		logger.trace(buildMarker(msgs), msgs);
	}

	private String buildMarker(Object... msgs) {
		StringBuilder strb = new StringBuilder();
		strb.append(LogData.instance().getId()).append("|");
		for (Object _ : msgs) {
			strb.append("{} ");
		}
		return strb.toString();
	}
}
