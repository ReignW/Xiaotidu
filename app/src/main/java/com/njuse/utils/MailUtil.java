package com.njuse.utils;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Created by Administrator on 2018/2/20.
 */

public class MailUtil {
//
//    public static Properties createConfiguration() {
//        return new Properties() {
//            {
//                put("mail.smtp.auth", "true");
//                put("mail.smtp.host", "smtp.qq.com");
//                put("mail.smtp.port", "587");
//                put("mail.smtp.starttls.enable", "true");
//            }
//        };
//    }
//
//    public static class SmtpAuthenticator extends Authenticator {
//
//        private String username = "876684433@qq.com";
//        private String password = "zuhietzjajcpbbje";
//
//        @Override
//        public PasswordAuthentication getPasswordAuthentication() {
//            return new PasswordAuthentication(username, password);
//        }
//    }

    /**
     * 创建一封只包含文本的简单邮件
     *
     * @param session 和服务器交互的会话
     * @param sendMail 发件人邮箱
     * @param receiveMail 收件人邮箱
     * @return
     * @throws Exception
     */
    public static MimeMessage createMimeMessage(Session session, String sendMail, String receiveMail,String identifyingCode,String subject) throws Exception {
        // 1. 创建一封邮件
        MimeMessage message = new MimeMessage(session);
        // 2. From: 发件人
        message.setFrom(new InternetAddress(sendMail, "小题督", "UTF-8"));
        // 3. To: 收件人（可以增加多个收件人、抄送、密送）
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, "亲爱的用户", "UTF-8"));
        // 4. Subject: 邮件主题
        message.setSubject(subject, "UTF-8");
        // 5. Content: 邮件正文（可以使用html标签）
        message.setContent(identifyingCode, "text/html;charset=UTF-8");
        // 6. 设置发件时间
        message.setSentDate(new Date());
        // 7. 保存设置
        message.saveChanges();
        return message;
    }
}
