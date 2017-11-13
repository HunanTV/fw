package com.hunantv.fw.utils;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;

public class SysConf {

    private static String env;
    private String confPath;
    private String sysPath;
    private String filename;

    public SysConf(String filename) {
        this.filename = filename;
    }

    public SysConf() {
    }

    static {
        System.out.println("==========sysconf start===========");
        env = System.getProperty("fw.project.env", "");
        System.out.println("Current project env is [" + (StringUtil.isNotBlank(env) ? env : "default") + "]");
        System.out.println("============sysconf end============");
    }

    public Properties read(String filename) throws Exception {
        this.filename = filename;
        return read();
    }

    public Properties read() throws Exception {
        String configFilePath = getConfigFilePath();
        Properties props = new Properties();
        System.out.println("====== loading " + configFilePath + " start ======");
        try(InputStream is = new java.io.FileInputStream(new File(configFilePath))){
            props.load(is);
        }
        props.put("system.path", sysPath);
        System.out.println("====== loading " + configFilePath + " end ======");
        return props;
    }

    /**
     * modified by xuyanbo
     * @return
     * @throws Exception
     */
    public String getConfigFileDir() throws Exception {
        return sysPath() + "confs" + File.separator;
    }

    /**
     * modified by xuyanbo
     * @return
     * @throws Exception
     */
    public String getConfigFilePath() throws Exception {
        if (confPath != null) {
            return confPath;
        }
        sysPath = sysPath();
        // 优先读取对应环境下的配置
        String envConfPath = sysPath + "confs" + File.separator +
                (StringUtil.isNotBlank(env) ? File.separator : "") + filename;
        File confFile = new File(envConfPath);
        if(confFile.exists()) {
            confPath = envConfPath;
        } else {
            // 其次读取confs根目录下的配置
            confPath = sysPath + "confs" + File.separator + filename;
        }
        return confPath;
    }

    public String getSysPath() throws Exception {
        if (sysPath != null) {
            return sysPath;
        }
        sysPath = sysPath();
        return sysPath;
    }

    private String sysPath() throws Exception {
        ClassLoader cl = this.getClass().getClassLoader();
        String classname = this.getClass().getName().replace('.', '/') + ".class";
        String res = null;
        if (cl != null) {
            java.net.URL url = cl.getResource(classname);
            if (url != null) {
                res = new File(".").getAbsolutePath();
                res = res.substring(0, res.length() - 1);
            }
        }
        return java.net.URLDecoder.decode(res, "UTF-8");
    }

    public static void main(String[] args) throws Exception {
        SysConf sc = new SysConf("logback.xml");
        System.out.println(sc.read());
    }
}
