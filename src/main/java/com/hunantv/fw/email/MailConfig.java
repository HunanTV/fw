package com.hunantv.fw.email;

import java.util.Properties;

import com.hunantv.fw.utils.SysConf;

public class MailConfig {
    private static final String CONFIG_FILE = "mail.properties";

    // ///////////////////////////
    // 发送者邮箱的相关设置
    // //////////////////////////
    static String SENDER_HOST; // 发送邮箱的host
    static String SENDER_NAME; // 发送邮箱的用户
    static String SENDER_PASSWORD; // 发送邮箱的密码
    static String SENDER_FROM; // 发送邮箱的帐号(email)

    // ///////////////////////////
    // 接收者的相关设置
    // //////////////////////////
    static String RECEIVER_TO; // 接收人邮箱(多个接收人用,分割)
    static String RECEIVER_CC; // 抄送人邮箱(多个邮箱用,分割)
    static String RECEIVER_BCC; // 密送人邮箱(多个邮箱用,分割)

    static void load() {

        SysConf conf = new SysConf();
        Properties mailConf = null;
        try {
            mailConf = conf.read(CONFIG_FILE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (mailConf == null)
            return;

        SENDER_HOST = mailConf.getProperty("mail.sender.host", "smtp.163.com");
        SENDER_NAME = mailConf.getProperty("mail.sender.name", "csdnku");
        SENDER_PASSWORD = mailConf.getProperty("mail.sender.password",
                "mima123");
        SENDER_FROM = mailConf
                .getProperty("mail.sender.from", "csdnku@163.com");

        RECEIVER_TO = mailConf.getProperty("mail.receiver.to",
                "mpp_service@163.com");
        RECEIVER_CC = mailConf.getProperty("mail.receiver.cc", "");
        RECEIVER_BCC = mailConf.getProperty("mail.receiver.bcc", "");
    }
}
