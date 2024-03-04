package tn.esprit.tools;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailSender {
    public static void sendEmail(String recipient, String subject, String content) {
        final String username = "espritedusmart@gmail.com";
        final String password = "zzby zdgz upww zrjb";

        // Set up the SMTP server properties
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.starttls.required", "true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");


        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);

            // Set From: header field
            message.setFrom(new InternetAddress("espritedusmart@gmail.com"));

            // Set To: header field
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));

            // Set Subject: header field
            message.setSubject(subject);

            // Set the actual message
            message.setText(content);

            // Send the message
            Transport.send(message);

            System.out.println("Sent message successfully....");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
