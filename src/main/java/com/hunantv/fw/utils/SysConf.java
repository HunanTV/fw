package com.hunantv.fw.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Properties;

public class SysConf {
    private static String confPath;

    private static String sysPath;

    private static String webPath;
    
    private static String envPath = "";

    private String filename;

    public SysConf(String filename) {
        this.filename = filename;
    }

    public SysConf() {
    }

    static {
        System.out.println("===============================");
        String env = System.getProperty("fw.project.env", "");
        if (!StringUtil.isEmpty(env)) {
            System.out.println("Current project env is [" + env + "]");
            envPath = env + File.separator;
        }else{
            System.out.println("Current project env is default.");
        }
        System.out.println("===============================");
    }

    public Properties read(String filename) throws Exception {
        this.filename = filename;
        return read();
    }

    public Properties read() throws Exception {
        String path = getConfPath();
        Properties props = new Properties();
        try(InputStream is = new java.io.FileInputStream(new File(path + filename))){
            props.load(is);
        }
        props.put("system.path", sysPath);
        return props;
    }

    public String getConfUri() throws Exception {
        return "file:" + this.getConfPath();
    }

    public String getConfPath() throws Exception {
        if (confPath != null)
            return confPath;
        sysPath = sysPath();
        confPath = sysPath + "confs" + File.separator + envPath;
        return confPath;
    }

    public String getSysUri() throws Exception {
        return "file:" + this.getSysPath();
    }

    public String getSysPath() throws Exception {
        if (sysPath != null)
            return sysPath;
        sysPath = sysPath();
        confPath = sysPath + "confs" + File.separator + envPath;
        return sysPath;
    }

    public String getWebPath() throws Exception {
        if (webPath != null)
            return webPath;
        sysPath();
        return webPath;
    }

    private String sysPath() throws Exception {
        ClassLoader cl = this.getClass().getClassLoader();
        String classname = this.getClass().getName().replace('.', '/') + ".class";
        String res = null;
        if (cl != null) {
            java.net.URL url = cl.getResource(classname);
            if (url != null) {
                String path = url.getFile();
                int fileStrPosition = path.indexOf("file:/");
                int begin = 0;
                int end = path.length();
                if (fileStrPosition >= 0)
                    begin = fileStrPosition + 5;
                end = path.indexOf("WEB-INF/");
                if (end > 0) {
                    String rf = path.substring(begin, end);
                    webPath = rf;
                    File f = new File(rf + "WEB-INF/conf/");
                    if (f.exists())
                        res = new File(rf + "WEB-INF/").getAbsolutePath() + File.separator;
                    else
                        res = new File(rf).getParentFile().getAbsolutePath() + File.separator;
                } else {
                    res = new File(".").getAbsolutePath();
                    res = res.substring(0, res.length() - 1);
                }
            }
        }
        return java.net.URLDecoder.decode(res, "UTF-8");
    }

    public Properties readXML(String xmlName) throws Exception {
        this.filename = xmlName;
        return readXML();
    }

    public Properties readXML() throws Exception {
        String path = getConfPath();
        Properties props = new Properties();
        InputStream is = new java.io.FileInputStream(new File(path + filename));
        props.loadFromXML(is);
        is.close();
        // props.put("system.path", sysPath);
        return props;
    }

    public void storeXML(Properties prop) throws Exception {
        String path = getConfPath();
        prop.storeToXML(new FileOutputStream(new File(path + filename)), "Store By Program");
    }

    public static void main(String[] args) throws Exception {
        SysConf sc = new SysConf("log4j.conf");
        System.out.println(sc.read());
    }
}
