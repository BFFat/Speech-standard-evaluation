package com.app.tools;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import com.sun.mail.util.MailSSLSocketFactory;


/**
 * 
 * @author Qixuan.Chen
 */
public class SendEmail {
    
    public static final String HOST = "smtp.exmail.qq.com";
    public static final String PROTOCOL = "smtp";
    public static final int PORT = 465;
    public static final String FROM = "admin@playrust.shop";//发件人的email
    public static final String PWD = "19850402Zt";//发件人密码
    
    /**
     * 获取Session
     * @return
     */
    private static Session getSession() {
        Properties props = new Properties();
        props.put("mail.smtp.host", HOST);//设置服务器地址
        props.put("mail.store.protocol" , PROTOCOL);//设置协议
        props.put("mail.smtp.port", PORT);//设置端口
        props.put("mail.smtp.auth" , true);
        
        Authenticator authenticator = new Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM, PWD);
            }
            
        };
        Session session = Session.getDefaultInstance(props , authenticator);
        
        return session;
    }
    
    public static void send(String toEmail ,String Subject ,String content) {
        Session session = getSession();
        try {
            System.out.println("--send--"+content);
            // Instantiate a message
            Message msg = new MimeMessage(session);
 
            //Set message attributes
            msg.setFrom(new InternetAddress(FROM));
            InternetAddress[] address = {new InternetAddress(toEmail)};
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject(Subject);
            msg.setSentDate(new Date());
            msg.setContent(content , "text/html;charset=utf-8");
 
            //Send the message
            Transport.send(msg);
        }
        catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
    
    @SuppressWarnings("static-access")
    public static void sendMessage(String alias,String to, String subject,  
            String messageText, String messageType) throws MessagingException, GeneralSecurityException, UnsupportedEncodingException {  
        // 第一步：配置javax.mail.Session对象  
        System.out.println("为" + HOST + "配置mail session对象");  


        Properties props = new Properties();  
        props.put("mail.smtp.host", HOST);
        props.put("mail.smtp.starttls.enable", "true");// 使用 STARTTLS安全连接
        props.put("mail.smtp.auth", "true"); // 使用验证
        // props.put("mail.debug", "true");

        //-------当需使用SSL验证时添加，邮箱不需SSL验证时删除即可（测试SSL验证使用QQ企业邮箱）
        String SSL_FACTORY="javax.net.ssl.SSLSocketFactory"; 
        props.put("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.port", "465"); //google使用465或587端口
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true); 
        props.put("mail.smtp.ssl.socketFactory", sf);
        //-------
        
        Session mailSession = Session.getInstance(props,new MyAuthenticator(FROM,PWD));  

        // 第二步：编写消息  
        System.out.println("编写消息from——to:" + FROM + "——" + to);  

        InternetAddress fromAddress = new InternetAddress(FROM,alias);  
        InternetAddress toAddress = new InternetAddress(to);  

        MimeMessage message = new MimeMessage(mailSession);  

        message.setFrom(fromAddress);  
        message.addRecipient(RecipientType.TO, toAddress);  

        message.setSentDate(Calendar.getInstance().getTime());  
        message.setSubject(subject);  
        message.setContent(messageText, messageType);  

        // 第三步：发送消息  
        Transport transport = mailSession.getTransport("smtp");  
        transport.connect(HOST,FROM, PWD);  
        transport.send(message, message.getRecipients(RecipientType.TO));  
        System.out.println("message yes");  
    }
    
    
    @SuppressWarnings("static-access")
    public static void sendMessageWithFile(String alias,String to, String subject,  
            String messageText, String messageType,String filename) throws MessagingException, GeneralSecurityException, UnsupportedEncodingException {  
        // 第一步：配置javax.mail.Session对象  
        System.out.println("为" + HOST + "配置mail session对象");  


        Properties props = new Properties();  
        props.put("mail.smtp.host", HOST);
        props.put("mail.smtp.starttls.enable", "true");// 使用 STARTTLS安全连接
        props.put("mail.smtp.auth", "true"); // 使用验证
        // props.put("mail.debug", "true");

        //-------当需使用SSL验证时添加，邮箱不需SSL验证时删除即可（测试SSL验证使用QQ企业邮箱）
        String SSL_FACTORY="javax.net.ssl.SSLSocketFactory"; 
        props.put("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.port", "465"); //google使用465或587端口
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true); 
        props.put("mail.smtp.ssl.socketFactory", sf);
        //-------
        
        Session mailSession = Session.getInstance(props,new MyAuthenticator(FROM,PWD));  

        // 第二步：编写消息  
        System.out.println("编写消息from——to:" + FROM + "——" + to);  

        InternetAddress fromAddress = new InternetAddress(FROM,alias);  
        InternetAddress toAddress = new InternetAddress(to);  

        MimeMessage message = new MimeMessage(mailSession);  

        message.setFrom(fromAddress);  
        message.addRecipient(RecipientType.TO, toAddress);  

        message.setSentDate(Calendar.getInstance().getTime());  
        message.setSubject(subject);  

     // 创建消息部分
        BodyPart messageBodyPart = new MimeBodyPart();

        // 消息
        messageBodyPart.setText(messageText);

        // 创建多重消息
        Multipart multipart = new MimeMultipart();

        // 设置文本消息部分
        multipart.addBodyPart(messageBodyPart);

        // 附件部分
        messageBodyPart = new MimeBodyPart();
        // 设置要发送附件的文件路径
        DataSource source = new FileDataSource(filename);
        messageBodyPart.setDataHandler(new DataHandler(source));

        // messageBodyPart.setFileName(filename);
        // 处理附件名称中文（附带文件路径）乱码问题
        messageBodyPart.setFileName(MimeUtility.encodeText("流水明细.pdf"));
        multipart.addBodyPart(messageBodyPart);
        
        message.setContent(multipart);
        
        // 第三步：发送消息  
        Transport transport = mailSession.getTransport("smtp");  
        transport.connect(HOST,FROM, PWD);  
        transport.send(message, message.getRecipients(RecipientType.TO));  
        System.out.println("message yes");  
    }
    
     
}


class MyAuthenticator extends Authenticator{  
    String userName="";  
    String password="";  
    public MyAuthenticator(){  

    }  
    public MyAuthenticator(String userName,String password){  
        this.userName=userName;  
        this.password=password;  
    }  
     protected PasswordAuthentication getPasswordAuthentication(){     
        return new PasswordAuthentication(userName, password);     
     }   
}