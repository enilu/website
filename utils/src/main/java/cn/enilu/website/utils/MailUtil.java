package cn.enilu.website.utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


/**
 * Created  on  2018/7/16 0016
 * MailUtil
 *
 * @author enilu
 */
public final class MailUtil {
    public static final String HOST = "smtp.xinshucredit.com";
    public static final String USER_NAME = "notify@xinshucredit.com";
    public static final String PASSWORD = "!@#QAS##1222";


    public static void main(String[] args) {
        // 收件人电子邮箱
        String to = "zhangtao@xinshucredit.com";
        String subject = "This is the Subject Line!";
        String text = "<table border='1'><tr><td>类型</td><td>端到端</td></tr></table>";
        MailUtil me = new MailUtil();
        send(to, subject, text);
    }

    public static  void send(String to, String subject, String text) {


        // 获取系统属性
        Properties properties = System.getProperties();

        // 设置邮件服务器
        properties.setProperty("mail.smtp.host", HOST);
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.port", "25");
        Session session = Session.getDefaultInstance(properties,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(
                                USER_NAME, PASSWORD);
                    }
                });
        try {
            // 创建默认的 MimeMessage 对象
            MimeMessage message = new MimeMessage(session);

            // Set From: 头部头字段
            message.setFrom(new InternetAddress(USER_NAME));

            // Set To: 头部头字段
            if(to.contains(";")){
             String[] toArr = to.split(";");
                InternetAddress[] addresses = new InternetAddress[toArr.length];
                for(int i=0;i<toArr.length;i++){
                    addresses[i] = new InternetAddress( toArr[i]);
                }
                message.addRecipients(Message.RecipientType.TO,
                        addresses);

            }else {
                message.addRecipient(Message.RecipientType.TO,
                        new InternetAddress(to));
            }

            // Set Subject: 头部头字段
            message.setSubject(subject);

            // 设置消息体
            message.setContent(text,"text/html;charset=UTF-8");

            // 发送消息
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
