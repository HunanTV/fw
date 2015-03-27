package com.hunantv.fw.fmext;

import java.io.File;

import org.apache.log4j.Logger;

import com.hunantv.fw.Application;
import com.hunantv.fw.utils.SysConf;

import freemarker.cache.SoftCacheStorage;
import freemarker.template.Configuration;

public class FmExt {
	private static final Logger logger = Logger.getLogger(FmExt.class);
	private Configuration freeMarkerCfg;
	private static FmExt fmExt = null;

	private FmExt() {
		try {
			SysConf sysConf = Application.getInstance().getSysConf();
			freeMarkerCfg = new Configuration();
			freeMarkerCfg.setDirectoryForTemplateLoading(new File(sysConf.getSysPath() + "views"));
			freeMarkerCfg.setDefaultEncoding("UTF-8");
			freeMarkerCfg.setSharedVariable("block", new BlockDirective());
			freeMarkerCfg.setSharedVariable("override", new OverrideDirective());
			freeMarkerCfg.setSharedVariable("extends", new ExtendsDirective());
			freeMarkerCfg.setClassicCompatible(true);
			freeMarkerCfg.setCacheStorage(new SoftCacheStorage());
			logger.info("init freemarker ok");
		} catch (Exception ex) {
			logger.error("init freemarker failed", ex);
			throw new RuntimeException(ex);
		}
	}

	public static FmExt getInstance() {
		if (null == fmExt)
			fmExt = new FmExt();
		return fmExt;
	}

	public Configuration getFreeMarkerCfg() {
		return this.freeMarkerCfg;
	}
}
