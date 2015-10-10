package com.hunantv.fw.email;

public class MailUtil {
    private static Mail mail;

    static {
        MailConfig.load();
        mail = new Mail(MailConfig.RECEIVER_TO, MailConfig.RECEIVER_CC,
                MailConfig.RECEIVER_BCC, MailConfig.SENDER_FROM,
                MailConfig.SENDER_HOST, MailConfig.SENDER_NAME,
                MailConfig.SENDER_PASSWORD);
    }

    /**
     * 
     * @param subject
     *            邮件的主题
     * @param content
     *            邮件的正文
     * @return true 表示发送成功 ; false 表示发送失败
     */
    public static boolean sendTextMail(String subject, String content) {
        return mail.sendMail(subject, content, null);
    }

    /**
     * 
     * @param subject
     *            邮件的主题
     * @param content
     *            邮件的正文
     * @param file
     *            附件
     * @return true 表示发送成功 ; false 表示发送失败
     */
    public static boolean sendMailWithAttachment(String subject,
            String content, String file) {
        return mail.sendMail(subject, content, file);
    }
}
