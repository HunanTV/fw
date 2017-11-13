package com.hunantv.fw.log;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


class LoggerData {
    private String id;
    private List<String> data;

    public LoggerData() {
        this(UUID.randomUUID().toString().replaceAll("-", ""));
    }

    public LoggerData(String id) {
        this.id = id;
        data = new ArrayList<String>();
    }

    public void add(String key, String value) {
        this.data.add(key + "=" + value);
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        if (data.size() == 0) {
            return "";
        }
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

    private static ThreadLocal<LoggerData> threadLocalVar = new ThreadLocal<LoggerData>();

    static {
        try {
            File file=new File(System.getProperty("user.dir")+File.separator+"confs/logback.xml");
            if(file.exists()){
                LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
                JoranConfigurator configurator = new JoranConfigurator();
                configurator.setContext(context);
                context.reset();
                configurator.doConfigure(file);
                FwLogger.getLogger(FwLogger.class).debug("use confs/logback.xml logback.xml");
            }else{
                FwLogger.getLogger(FwLogger.class).debug("use default logback.xml");
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private Logger logger = null;

    public FwLogger(Class<?> clazz) {
        this(clazz.getName());
    }

    public FwLogger(String clazz) {
        logger = LoggerFactory.getLogger(clazz);
    }

    public static FwLogger getLogger(Class<?> clz) {
        return new FwLogger(clz);
    }

    public static FwLogger getLogger(String clz) {
        return new FwLogger(clz);
    }

    public String getSeqid() {
        initSeqid();
        LoggerData data = (LoggerData) threadLocalVar.get();
        return data.getId();
    }

    public void delayInfo(String key, Object msg) {
        initSeqid();
        LoggerData data = (LoggerData) threadLocalVar.get();
        data.add(key, msg.toString());
    }

    public void initSeqid() {
        LoggerData data = (LoggerData) threadLocalVar.get();
        if (data == null) {
            FwLogger.threadLocalVar.set(new LoggerData());
        }
    }

    public void clearSeqid() {
        LoggerData data = (LoggerData) threadLocalVar.get();
        //this.info(data.toString());
        FwLogger.threadLocalVar.remove();
    }

    public void debug(Object message) {
        logger.debug(buildMsg(message));
    }

    public void debug(Object message, Throwable t) {
        logger.debug(buildMsg(message), t);
    }

    public void debug(String message,Object ...args){
        logger.debug(message,args);
    }

    public void info(Object message) {
        logger.info(buildMsg(message));
    }

    public void info(String  message,Object ... objects){
        logger.info(message,objects);
    }

    public void info(Object message, Throwable t) {
        logger.info(buildMsg(message), t);
    }


    public void warn(Object message) {
        logger.warn(buildMsg(message));
    }

    public void warn(String message,Object ... objects){
        logger.warn(message,objects);
    }

    public void warn(Object message, Throwable t) {
        logger.warn(buildMsg(message), t);
    }

    public void error(Object message) {
        logger.error(buildMsg(message));
    }

    public void error(String message,Object ... objects){
        logger.error(message,objects);
    }

    public void error(Object message, Throwable t) {
        logger.error(buildMsg(message), t);
    }

    public void trace(Object message) {
        logger.trace(buildMsg(message));
    }

    public void trace(String message,Object ... objects){
        logger.trace(message,objects);
    }


    public void trace(Object message, Throwable t) {
        logger.trace(buildMsg(message), t);
    }

    private String buildMsg(Object message) {
        return this.getSeqid() + "|" + message.toString();
    }
}
