
### 代码片段

    <dependency>
        <groupId>javax.mail</groupId>
        <artifactId>mail</artifactId>
        <version>1.4.1</version>
        </dependency>
    </dependencies>

    import javax.activation.DataHandler;
    import javax.activation.FileDataSource;
    import javax.mail.*;
    import javax.mail.internet.*;
    import java.io.FileNotFoundException;
    import java.io.FileOutputStream;
    import java.text.SimpleDateFormat;
    import java.util.Date;
    import java.util.Properties;

    /**
     * 发送简单邮件
     */
    public static void sendMail() {
        //
        String host = "smtp.163.com";
        String user = "keephunny@163.com";
        String password = "aa13063279771";
        String fromMail = "keephunny@163.com";
        String toMail = "85103609@qq.com";
        String[] ccMails = new String[]{"keephunny@163.com", "13705690151@139.com"};

        String[] attachments = {"/Users/apple/Desktop/代码评审20171110.pdf", "/Users/apple/Desktop/英语.txt"};

        Properties prop = new Properties();
        prop.setProperty("mail.host", "smtp.163.com");
        prop.setProperty("mail.transport.protocol", "smtp");
        prop.setProperty("mail.smtp.auth", "true");

        //1.创建session
        Session session = Session.getInstance(prop);
        //开启session的debug模式，可以看到程序发送email的状态
        session.setDebug(true);

        //2.通过session得到transport对象
        Transport ts = null;
        try {
            ts = session.getTransport();

        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }

        //3.使用邮箱的用户名和密码连上邮件服务器
        try {
            ts.connect(host, user, password);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        //4.创建邮件
        //Message message = createSimpleMail(session, fromMail, toMail, ccMails);
        Message message = createAttachMail(session, fromMail, toMail, attachments);

        //5.发送邮件
        try {
            ts.sendMessage(message, message.getAllRecipients());
        } catch (MessagingException e) {
            e.printStackTrace();
        } finally {
            if (ts == null) {
                return;
            }
            try {
                ts.close();
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 创建简单邮件
     *
     * @param session  当前session会话
     * @param fromMail 发件人
     * @param toMail   收件人
     * @param ccMails  抄送人
     * @return 返回邮件对象
     */
    private static MimeMessage createSimpleMail(Session session, String fromMail, String toMail, String[] ccMails) {
        //创建邮件对象
        MimeMessage message = new MimeMessage(session);
        //设置邮件的发件人 收件人
        try {
            //TODO 收件人可以多个
            message.setFrom(new InternetAddress(fromMail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(toMail));

            //邮件抄送
            if (ccMails != null && ccMails.length > 0) {
                Address[] ccAdresses = new InternetAddress[ccMails.length];
                for (int i = 0; i < ccMails.length; i++) {
                    ccAdresses[i] = new InternetAddress(ccMails[i]);
                }
                message.setRecipients(Message.RecipientType.CC, ccAdresses);

            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //邮件的标题
            message.setSubject("只包含文本的简单邮件，" + simpleDateFormat.format(new Date()));
            //邮件的文本内容
            message.setContent("你好啊！", "text/html;charset=UTF-8");

        } catch (MessagingException e) {
            e.printStackTrace();
            return null;
        }
        /**
         * 设置收件人地址（可以增加多个收件人、抄送、密送），即下面这一行代码书写多行
         * MimeMessage.RecipientType.TO:发送
         * MimeMessage.RecipientType.CC：抄送
         * MimeMessage.RecipientType.BCC：密送
         */

        //返回创建好的邮件对象
        return message;
    }

    /**
     * 创建带附件的邮件
     *
     * @param session
     * @return
     */
    private static MimeMessage createAttachMail(Session session, String fromMail, String toMail, String[] attachments) {
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(fromMail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(toMail));
            message.setSubject("邮件标题");
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        //创建邮件正文
        MimeMultipart mp = new MimeMultipart();
        MimeMultipart mpAttach = new MimeMultipart();
        MimeBodyPart text = new MimeBodyPart();
        try {
            text.setContent("<h3>附件内容</h3> <img src='cid:QQ20190314-173201@2x.jpg'>d", "text/html;charset=UTF-8");
            mp.addBodyPart(text);

            //图片
            MimeBodyPart image = new MimeBodyPart();
            image.setDataHandler(new DataHandler(new FileDataSource("/Users/apple/Desktop/QQ20190314-173201@2x.png")));
            image.setContentID("QQ20190314-173201@2x.jpg");


            if (attachments != null) {
                //创建邮件附件
                for (int i = 0; i < attachments.length; i++) {
                    MimeBodyPart attach = new MimeBodyPart();
                    //DataSource dataSource1=new ByteArrayDataSource(InputStream, "application/png");
                    //DataSource dataSource=new URLDataSource(new URL(""));   dataHandler=new DataHandler(dataSource1);
                    DataHandler dh = new DataHandler(new FileDataSource(attachments[i]));
                    attach.setDataHandler(dh);
                    attach.setFileName(dh.getName());

                    //创建容器描述与数据关系
                    mpAttach.addBodyPart(attach);
                }
            }
            mp.addBodyPart(image);
            mp.setSubType("related");

            MimeBodyPart content = new MimeBodyPart();
            content.setContent(mp);
            mpAttach.addBodyPart(content);
            mpAttach.setSubType("mixed");


            message.setContent(mpAttach);
            message.saveChanges();
            //设置邮件消息发送的时间
            message.setSentDate(new Date());
        } catch (MessagingException e) {
            e.printStackTrace();
        }


        return message;
    }


### jmail发送二进制流附件

    //DataSource dataSource=new FileDataSource("d:/1.png");
    URL url = new URL("https://www.baidu.com/5.png");
    DataSource dataSource=new URLDataSource(url);
    DataHandler dataHandler=new DataHandler(dataSource);
    messageBodyPart.setDataHandler(dataHandler);

     InputStream is=getInputStream();
    //DataSource dataSource1=new FileDataSource("d:/aa.doc");
    DataSource dataSource1=new ByteArrayDataSource(is, "application/png");
    dataHandler=new DataHandler(dataSource1);
    messageBodyPart.setDataHandler(dataHandler)