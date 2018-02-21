package com.njuse.utils;

import java.util.Properties;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

/**
 * Created by Administrator on 2018/2/21.
 */

public class EmailSender {
    //发送验证码到用户邮箱
    public static boolean sendEmail(final String receiver, final String emailContent) {
        // 1. 创建参数配置, 用于连接邮件服务器的参数配置
        Properties props = new Properties();          // 参数配置
        props.setProperty("mail.transport.protocol", "smtp");  // 使用的协议（JavaMail规范要求）
//        props.setProperty("mail.smtp.host", "smtp.163.com");   // 网易邮箱的 SMTP 服务器地址，已废弃
        props.setProperty("mail.smtp.host", "smtp.yeah.net");   // 发件人的邮箱的 SMTP 服务器地址
        props.setProperty("mail.smtp.auth", "true");      // 需要请求认证
        props.setProperty("mail.smtp.port", "25");
        //如果遇到ssl类错误，请打开一下代码
//        final String smtpPort = "587";
//        props.setProperty("mail.smtp.port", smtpPort);
//        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//        props.setProperty("mail.smtp.socketFactory.fallback", "false");
//        props.setProperty("mail.smtp.socketFactory.port", smtpPort);
        // 2. 根据配置创建会话对象, 用于和邮件服务器交互
        Session session = Session.getDefaultInstance(props);
        // 设置为debug模式, 可以查看详细的发送 log
        session.setDebug(true);
        // 3. 创建一封邮件
        MimeMessage message = null;//我这里是以163邮箱为发信邮箱测试通过
        try {
//            message = MailUtil.createMimeMessage(session, "13420146901@163.com", receiver, "验证码："+identifyingCode+"\n(注意！请不要泄露给他人！)\n\n如果不是你本人操作，请忽略该邮件！请勿回复！");
            message = MailUtil.createMimeMessage(session, "xiaotidu_info@yeah.net", receiver, emailContent);
            // 4. 根据 Session 获取邮件传输对象
            Transport transport = session.getTransport();
//            transport.connect("13420146901@163.com", "njuse2017");    //已废弃
            transport.connect("xiaotidu_info@yeah.net", "Manager0");
            // 6. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
            transport.sendMessage(message, message.getAllRecipients());
            // 7. 关闭连接
            transport.close();

        } catch (Exception e) {
            return false;
//            e.printStackTrace();
        }

        return true;
    }
}
