package com.aitao.myblog.utils;


import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * Author : AiTao
 * Date : 2020/10/26
 * Time : 10:30
 * Information : 邮箱工具类
 */
@Component
public class MailUtils implements Callable<String> {
    private final static String sendEmailPassword = "zbytzauxnxszbdad";//qq授权码
    private final static String sendEmailSMTPHost = "smtp.qq.com"; // 发件人邮箱的 SMTP 服务器地址
    private final static String sendEmailAccount = "1003448731@qq.com";//发件人
    private static String code;
    private final static Byte CODE_COUNT = 5;

    public static String getCode() {
        return code;
    }

    /**
     * 随机生成验证码
     */
    private static void randomCodes() {
        code = UUID.randomUUID().toString().replace("-", "").substring(0, CODE_COUNT);
    }

    /**
     * 发送邮件
     *
     * @param receiveMailAccount 收件人
     * @return
     * @throws Exception
     */
    private static String send(String receiveMailAccount) throws Exception {
        // 参数配置
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.host", sendEmailSMTPHost);
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        // 根据配置创建会话对象, 用于和邮件服务器交互
        Session session = Session.getDefaultInstance(props);
        // 设置为debug模式, 可以查看详细的发送 log
        session.setDebug(true);
        // 创建一封邮件
        Message message = createMimeMessage(session, sendEmailAccount, receiveMailAccount);
        // 根据 Session 获取邮件传输对象
        Transport transport = session.getTransport();
        // 使用 邮箱账号 和 密码 连接邮件服务器, 这里认证的邮箱必须与 message 中的发件人邮箱一致, 否则会报错
        transport.connect(sendEmailAccount, sendEmailPassword);
        // 发送邮件
        transport.sendMessage(message, message.getAllRecipients());
        // 关闭连接
        transport.close();
        return getCode();
    }

    /**
     * 创建邮件
     *
     * @param session     会话对象
     * @param sendMail    发件人
     * @param receiveMail 收件人
     * @return
     * @throws Exception
     */
    private static Message createMimeMessage(Session session, String sendMail, String receiveMail) throws Exception {
        Message message = new MimeMessage(session);
        //设置发件人
//        message.setFrom(new InternetAddress(sendMail));
        //设置自定义昵称
        String nick = MimeUtility.encodeText("DoraeLamon");
        message.setFrom(new InternetAddress(nick + " <" + sendMail + ">"));
        //设置收信人
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail));
        // 设置邮件标题
        message.setSubject(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
        // 设置邮件正文
        randomCodes();
        //设置发送日期
        message.setSentDate(new Date());
        //保存设置
        message.saveChanges();
        return message;
    }

    @Override
    public String call() throws Exception {
        return send("1906404524@qq.com");
    }


    public static void main(String[] args) {
        try {
            FutureTask<String> futureTask = new FutureTask<>(new MailUtils());
            new Thread(futureTask).start();
            String code = futureTask.get();
            System.out.println("code:" + code);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
