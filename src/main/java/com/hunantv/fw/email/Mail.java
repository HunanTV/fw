package com.hunantv.fw.email;

import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

import javax.activation.*;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import com.hunantv.fw.utils.StringUtil;

/**
 * <p>
 * Title: 使用javamail发送邮件
 * </p>
 */
public class Mail {

    private String to = ""; // 接收人邮箱(多个接收人用,分割)
    private String cc = ""; // 抄送人邮箱(多个邮箱用,分割)
    private String bcc = ""; // 密送人邮箱(多个邮箱用,分割)

    private String from = ""; // 发件邮箱
    private String host = ""; // 发件邮件服务器地址名称
    private String username = ""; // 发件邮箱的用户名
    private String password = ""; // 发件邮箱的密码

    private String filename = "";// 附件文件名
    private String subject = "";// 邮件主题
    private String content = "";// 邮件正文

    @SuppressWarnings("rawtypes")
    Vector file = new Vector();// 附件文件集合

    /**
     * 
     * @param to
     * @param cc
     * @param bcc
     * @param from
     * @param smtpServer
     * @param username
     * @param password
     */
    public Mail(String to, String cc, String bcc, String from,
            String smtpServer, String username, String password) {
        this.from = from;
        this.host = smtpServer;
        this.username = username;
        this.password = password;

        this.to = to;
        this.cc = cc;
        this.bcc = bcc;
    }

    /**
     * <br>
     * 方法说明：设置邮件主题 <br>
     * 输入参数： <br>
     * 返回类型：
     */
    private void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * <br>
     * 方法说明：设置邮件内容 <br>
     * 输入参数： <br>
     * 返回类型：
     */
    private void setContent(String content) {
        this.content = content;
    }

    /**
     * <br>
     * 方法说明：把主题转换为中文 <br>
     * 输入参数：String strText <br>
     * 返回类型：
     */
    @SuppressWarnings("unused")
    private String transferChinese(String strText) {
        try {
            strText = MimeUtility.encodeText(new String(strText.getBytes(),
                    "GB2312"), "GB2312", "B");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strText;
    }

    /**
     * <br>
     * 方法说明：往附件组合中添加附件 <br>
     * 输入参数： <br>
     * 返回类型：
     */
    @SuppressWarnings("unchecked")
    private void attachFile(String fname) {
        file.addElement(fname);
    }

    /**
     * <br>
     * 方法说明：发送邮件 <br>
     * 输入参数： <br>
     * 返回类型：boolean 成功为true，反之为false
     */
    private boolean send() {

        // 构造mail session
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.auth", "true");
        Session session = Session.getDefaultInstance(props,
                new Authenticator() {
                    public PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {
            // 构造MimeMessage 并设定基本的值
            MimeMessage msg = new MimeMessage(session);

            // 发件人
            msg.setFrom(new InternetAddress(from));

            // 收件人
            if (!StringUtil.isEmpty(to)) {
                msg.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(to));
            }

            // 抄送人
            if (!StringUtil.isEmpty(cc)) {
                msg.setRecipient(Message.RecipientType.CC, new InternetAddress(
                        cc));
            }

            // 暗送人
            if (!StringUtil.isEmpty(bcc)) {
                msg.setRecipient(Message.RecipientType.BCC,
                        new InternetAddress(bcc));
            }

            // 设置邮件消息发送的时间
            msg.setSentDate(new Date());

            // 邮件标题
            // subject = transferChinese(subject);
            msg.setSubject(subject);

            // 构造Multipart
            Multipart mp = new MimeMultipart();

            // 向Multipart添加正文
            // 设置邮件消息的主要内容
            MimeBodyPart mbpContent = new MimeBodyPart();
            // mbpContent.setContent(content, "text/html;charset=utf-8");
            // //charset=utf-8 charset=gb2312
            mbpContent.setText(content);

            // 向MimeMessage添加（Multipart代表正文）
            mp.addBodyPart(mbpContent);

            // 向Multipart添加附件
            @SuppressWarnings("rawtypes")
            Enumeration efile = file.elements();
            while (efile.hasMoreElements()) {

                MimeBodyPart mbpFile = new MimeBodyPart();
                filename = efile.nextElement().toString();
                FileDataSource fds = new FileDataSource(filename);
                mbpFile.setDataHandler(new DataHandler(fds));
                // 这个方法可以解决附件乱码问题
                String filename = new String(fds.getName().getBytes(),
                        "ISO-8859-1");

                mbpFile.setFileName(filename);
                // 向MimeMessage添加（Multipart代表附件）
                mp.addBodyPart(mbpFile);

            }

            file.removeAllElements();

            // 向Multipart添加MimeMessage
            msg.setContent(mp);
            msg.setSentDate(new Date());
            msg.saveChanges();

            // 发送邮件
            Transport transport = session.getTransport("smtp");
            transport.connect(host, username, password);
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();
        } catch (Exception mex) {
            mex.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean sendMail(String subject, // 邮件主题
            String content, // 邮件内容
            String attachment // 附件
    ) {

        setSubject(subject);
        setContent(content);
        if (!StringUtil.isEmpty(attachment)) {
            attachFile(attachment);
        }

        return send();
    }
}