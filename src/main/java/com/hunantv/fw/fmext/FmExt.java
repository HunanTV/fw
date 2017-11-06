package com.hunantv.fw.fmext;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hunantv.fw.Application;
import com.hunantv.fw.utils.SysConf;

import freemarker.cache.MruCacheStorage;
import freemarker.template.Configuration;

public class FmExt {
    private static Logger logger = LoggerFactory.getLogger(FmExt.class);
    private Configuration freeMarkerCfg;

    public static FmExt getInstance() {
        return FmExtHolder.instance;
    }

    private static class FmExtHolder {
        private static FmExt instance = new FmExt();
    }

    private FmExt() {
        try {
            Application app = Application.getInstance();
            SysConf sysConf = app.getSysConf();
            freeMarkerCfg = new Configuration();
            String viewPath = sysConf.getSysPath() + "views";
            File viewDir = new File(viewPath);
            if (!viewDir.exists()) {
                viewDir.mkdirs();
            }
            freeMarkerCfg.setDirectoryForTemplateLoading(viewDir);
            freeMarkerCfg.setDefaultEncoding("UTF-8");
            freeMarkerCfg.setSharedVariable("block", new BlockDirective());
            freeMarkerCfg.setSharedVariable("override", new OverrideDirective());
            freeMarkerCfg.setSharedVariable("extends", new ExtendsDirective());
            freeMarkerCfg.setClassicCompatible(true);
            freeMarkerCfg.setCacheStorage(new MruCacheStorage(0, Integer.MAX_VALUE));
            if (!app.isDebug())
                freeMarkerCfg.setTemplateUpdateDelay(360 * 24 * 3600); // 1年更新一次
            logger.info("init freemarker ok");
        } catch (Exception ex) {
            logger.error("init freemarker failed", ex);
            throw new RuntimeException(ex);
        }
    }

    public Configuration getFreeMarkerCfg() {
        return this.freeMarkerCfg;
    }
}
