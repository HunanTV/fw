package com.mchange.v2.c3p0.cfg;

import com.hunantv.fw.utils.SysConf;

import java.io.FileInputStream;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by thomas on 14-10-29.
 */
public class FilePathPropFinder extends DefaultC3P0ConfigFinder {
    private static String FPATH;

    static {
        SysConf sysConf = new SysConf();
        try {
            FPATH = (sysConf.getConfPath() + "c3p0.properties");
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public C3P0Config findConfig() throws Exception {
        C3P0Config defaults =  super.findConfig();
        Properties props = new Properties();
        props.load(new FileInputStream(Paths.get(FPATH).toFile()));

        Map map = new HashMap(props.size());
        props.entrySet().stream().forEach(entry -> {
            String key = (String)entry.getKey();
            if (key.startsWith("c3p0.")) {
                map.put(key.substring(5), entry.getValue());
            }
        });

        defaults.defaultConfig.props.putAll(map);
        return defaults;
    }
}
