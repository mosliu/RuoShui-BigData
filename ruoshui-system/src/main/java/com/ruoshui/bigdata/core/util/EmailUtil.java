package com.ruoshui.bigdata.core.util;

import com.sun.mail.util.MailSSLSocketFactory;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;



public class EmailUtil {

    public static void send(String emailUserName,String emailPassword,String emailAuthorization,String SJemailUserName,String title,String msg) throws Exception{
        Properties properties = new Properties();
        //设置QQ邮件服务器
        properties.setProperty("mail.host","smtp.qq.com");
        //邮件发送协议
        properties.setProperty("mail.transport.protocol","smtp");
        //需要验证用户名密码
        properties.setProperty("mail.smtp.auth","true");
        //还要设置SSL加密，加上以下代码即可
        MailSSLSocketFactory mailSSLSocketFactory = new MailSSLSocketFactory();
        mailSSLSocketFactory.setTrustAllHosts(true);
        properties.put("mail.smtp.ssl.enable","true");
        properties.put("mail.smtp.ssl.socketFactory",mailSSLSocketFactory);
        //使用JavaMail发送邮件的5个步骤
        //1、创建定义整个应用程序所需环境信息的 Session 对象
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                //发件人用户名，授权码
                return new PasswordAuthentication(emailUserName,emailAuthorization);
            }
        });
        //开启Session的debug模式，这样就可以查看程序发送Email的运行状态
        session.setDebug(true);
        //2、通过session得到transport对象
        Transport transport = session.getTransport();
        //3、使用用户名和授权码连上邮件服务器
        transport.connect("smtp.qq.com",emailUserName,emailPassword);
        //4、创建邮件：写邮件
        //注意需要传递Session
        MimeMessage message = new MimeMessage(session);
        //指明邮件的发件人
        message.setFrom(new InternetAddress(emailUserName));
        //指明邮件的收件人，现在发件人和收件人是一样的，就是自己给自己发
        message.setRecipient(Message.RecipientType.TO , new InternetAddress(SJemailUserName));
        message.setSubject(title);
        message.setContent(msg,"text/html;charset=UTF-8");
        //5、发送邮件
        transport.sendMessage(message,message.getAllRecipients());

        //6、关闭连接
        transport.close();
    }
}
