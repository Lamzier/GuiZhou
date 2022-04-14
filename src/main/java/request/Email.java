package main.java.request;

import java.util.*;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Email{

    private final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
    private String smtpServer; // SMTP服务器地址
    private String port; // 端口
    private String username; // 登录SMTP服务器的用户名
    private String password; // 登录SMTP服务器的密码
    private List<String> recipients = new ArrayList<String>(); // 收件人地址集合
    private String subject; // 邮件主题
    private String content; // 邮件正文
    private List<String> attachmentNames = new ArrayList<String>(); // 附件路径信息集合
    private static List<String> usernameAll = new ArrayList<>() , passwordAll = new ArrayList<>();//账号密码信息
    private static int maxEmail;//最大可发送邮箱数量


    /**
     * 初始化启动
     */
    public static boolean Start(){
        Date startDate = new Date();
        usernameAll.add("2948725521@qq.com");
        maxEmail = usernameAll.size();
        passwordAll.add("ifctowjmetojdhab");
        if (passwordAll.size() > maxEmail) maxEmail = passwordAll.size();
        Date endDate = new Date();
        long span = endDate.getTime() - startDate.getTime();
        System.out.println("邮箱初始化完毕！耗时：" + span + "毫秒");
        return true;
    }

    /**
     * 发送邮件含邮件 , 随机发件人
     * @param title 标题
     * @param content 内容
     * @param to 收件人集合
     * @param files 附件集合
     */
    public static boolean send(String title , String content , List<String> to , List<String> files){
        int index = (int)(Math.random() * maxEmail);//获取随机索引
        Email sendMailBySSL = new Email("smtp.qq.com", "465",
                usernameAll.get(index), passwordAll.get(index), to, title, content,
                files);
        return sendMailBySSL.sendMail();
    }

    /**
     * 发送邮件含邮件 , 随机发件人
     * 参数优化
     */
    public static boolean send(String title , String content , String to , List<String> files){
        List<String> toList = new ArrayList<>();
        toList.add(to);
        return send(title,content,toList,files);
    }

    /**
     * 发送邮件含邮件 , 随机发件人
     * 参数优化
     */
    public static boolean send(String title , String content , String to , String files){
        List<String> toList = new ArrayList<>();
        toList.add(to);
        List<String> filesList = new ArrayList<>();
        filesList.add(files);
        return send(title,content,toList,filesList);
    }

    /**
     * 发送邮件含邮件 , 随机发件人
     * 参数优化
     */
    public static boolean send(String title , String content , List<String> to , String files){
        List<String> filesList = new ArrayList<>();
        filesList.add(files);
        return send(title,content,to,filesList);
    }

    /**
     * 发送邮件不含邮件 , 随机发件人
     * @param title 标题
     * @param content 内容
     * @param to 收件人集合
     */
    public static boolean send(String title , String content , List<String> to){
        int index = (int)(Math.random() * maxEmail);//获取随机索引
        Email sendMailBySSL = new Email("smtp.qq.com", "465",
                usernameAll.get(index), passwordAll.get(index), to, title, content,
                new ArrayList<>());
        return sendMailBySSL.sendMail();
    }

    /**
     * 发送邮件不含邮件 , 随机发件人
     * 参数优化
     */
    public static boolean send(String title , String content , String to){
        List<String> toList = new ArrayList<>();
        toList.add(to);
        return send(title,content,toList);
    }

    /**
     * 发送邮件不含邮件 , 指定发件人
     * @param title 标题
     * @param content 内容
     * @param to 收件人集合
     * @param files 附件集合
     */
    public static boolean send(String title , String content , List<String> to , List<String> files , int from){
        Email sendMailBySSL = new Email("smtp.qq.com", "465",
                usernameAll.get(from), passwordAll.get(from), to, title, content,
                files);
        return sendMailBySSL.sendMail();
    }

    /**
     * 发送邮件含邮件 , 指定发件人
     * @param title 标题
     * @param content 内容
     * @param to 收件人集合
     */
    public static boolean send(String title , String content , List<String> to , int from){
        Email sendMailBySSL = new Email("smtp.qq.com", "465",
                usernameAll.get(from), passwordAll.get(from), to, title, content,
                new ArrayList<>());
        return sendMailBySSL.sendMail();
    }

    /**
     * 发送邮件含邮件 , 指定发件人
     * 参数优化
     */
    public static boolean send(String title , String content , String to , int from){
        List<String> toList = new ArrayList<>();
        toList.add(to);
        return send(title,content,toList,from);
    }


    private Email() {}

    private Email(String smtpServer, String port, String username,
                  String password, List<String> recipients, String subject,
                  String content, List<String> attachmentNames) {
        this.smtpServer = smtpServer;
        this.port = port;
        this.username = username;
        this.password = password;
        this.recipients = recipients;
        this.subject = subject;
        this.content = content;
        this.attachmentNames = attachmentNames;
    }

    private void setSmtpServer(String smtpServer) {
        this.smtpServer = smtpServer;
    }

    private void setPort(String port) {
        this.port = port;
    }

    private void setUsername(String username) {
        this.username = username;
    }

    private void setPassword(String password) {
        this.password = password;
    }

    private void setRecipients(List<String> recipients) {
        this.recipients = recipients;
    }

    private void setSubject(String subject) {
        this.subject = subject;
    }

    private void setContent(String content) {
        this.content = content;
    }

    private void setAttachmentNames(List<String> attachmentNames) {
        this.attachmentNames = attachmentNames;
    }

    /**
     * 进行base64加密，防止中文乱码
     */
    private String changeEncode(String str) {
        return str;//暂时不采用加密
        /*
        try {
            str = MimeUtility.encodeText(new String(str.getBytes(), "UTF-8"),
                    "UTF-8", "B"); // "B"代表Base64
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
         */
    }

    /**
     * 正式发邮件
     */
    private boolean sendMail() {
        Date startDate = new Date();
        Properties properties = new Properties();
        properties.put("mail.smtp.host", smtpServer);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.socketFactory.class", SSL_FACTORY); // 使用JSSE的SSL
        // socketfactory来取代默认的socketfactory
        properties.put("mail.smtp.socketFactory.fallback", "false"); // 只处理SSL的连接,对于非SSL的连接不做处理
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.socketFactory.port", port);
        properties.put("mail.smtp.ssl.enable", true);
        Session session = Session.getInstance(properties);
        session.setDebug(false);//关闭调试
        MimeMessage message = new MimeMessage(session);
        try {
            // 发件人
            Address address = new InternetAddress(username);
            message.setFrom(address);
            // 收件人
            for (String recipient : recipients) {
                //System.out.println("收件人：" + recipient);
                Address toAddress = new InternetAddress(recipient);
                message.setRecipient(MimeMessage.RecipientType.TO, toAddress); // 设置收件人,并设置其接收类型为TO
                System.out.println("邮件(to:" + recipient + "|title:" + subject + "|content:" + main.java.business.Article.getShowContent(content , 20) + ")设置成功！");
            }
            // 主题
            message.setSubject(changeEncode(subject));
            // 时间
            message.setSentDate(new Date());
            Multipart multipart = new MimeMultipart();
            // 添加文本
            BodyPart text = new MimeBodyPart();
            text.setText(content);
            multipart.addBodyPart(text);
            // 添加附件
            for (String fileName : attachmentNames) {
                BodyPart adjunct = new MimeBodyPart();
                FileDataSource fileDataSource = new FileDataSource(fileName);
                adjunct.setDataHandler(new DataHandler(fileDataSource));
                adjunct.setFileName(changeEncode(fileDataSource.getName()));
                multipart.addBodyPart(adjunct);
            }
            // 清空收件人集合，附件集合
            recipients.clear();
            attachmentNames.clear();
            message.setContent(multipart);
            message.saveChanges();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("邮件(title:" + subject + "|content:" + main.java.business.Article.getShowContent(content,20) + ")发送失败！");
            return false;
        }
        try {
            Transport transport = session.getTransport("smtp");
            transport.connect(smtpServer, username, password);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("邮件(title:" + subject + "|content:" + main.java.business.Article.getShowContent(content,20) + ")发送失败！");
            return false;
        }
        Date endDate = new Date();
        long span = endDate.getTime() - startDate.getTime();
        System.out.println("邮件(title:" + subject + "|content:" + main.java.business.Article.getShowContent(content,20) + ")发送成功！耗时：" + span + " 毫秒");
        return true;
    }
}
