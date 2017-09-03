package com.goldstardiana.gs.services;

import com.goldstardiana.gs.model.EmailConfig;
import com.goldstardiana.gs.model.EmailData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;

@Service
public class EmailService {
    private List<EmailData> unreadEmails;

    @Autowired
    private EmailConfig emailConfig;
    @Autowired
    private EmailDataService emailDataService;
    @Autowired
    URLProvider urlProvider;

    public List<EmailData> getUnreadEmails() {
        return emailDataService.receiveEmail(emailConfig.getHost(), emailConfig.getMailStoreType(), emailConfig.getLogin(), emailConfig.getPassword());
    }


    public void sendEmail(EmailData data) {
        String url = urlProvider.getUrlForProduct(data.getSelectedProduct());

        Properties props = new Properties();
        props.put("mail.smtp.host", "hosting10.ukrnames.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("content@goldstardiana.com","medusa333");
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("content@goldstardiana.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(data.getClientEmailAddress()));
            message.setSubject("You lucky url");
            message.setText("THIS IS URLAAAA!!!!" + url);

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
