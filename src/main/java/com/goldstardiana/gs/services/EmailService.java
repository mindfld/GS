package com.goldstardiana.gs.services;

import com.goldstardiana.gs.model.EmailConfig;
import com.goldstardiana.gs.model.EmailData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.List;
import java.util.Properties;

@Service
public class EmailService {

    @Autowired
    private EmailConfig emailConfig;
    @Autowired
    private EmailDataService emailDataService;
    @Autowired
    private URLProvider urlProvider;

    public List<EmailData> getUnreadEmails() {
        return emailDataService.receiveEmail(emailConfig.getHost(), emailConfig.getLogin(), emailConfig.getPassword());
    }


    public void sendEmail(EmailData data) {
        String url = urlProvider.getUrlForProduct(data.getSelectedProduct());


        try {
            MimeMessage message = new MimeMessage(configureEmailSession());

            message.setFrom(new InternetAddress(emailConfig.getSenderEmail()));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(data.getClientEmailAddress()));
            message.setSubject(emailConfig.getEmailSubject());
            Multipart multipart = new MimeMultipart("alternative");

            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(emailConfig.getEmailText().replace("%url%", url), "text/html; charset=utf-8");

            multipart.addBodyPart(htmlPart);
            message.setContent(multipart);

            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Done");
    }

    private Session configureEmailSession() {
        Properties props = new Properties();
        props.put("mail.smtp.host", emailConfig.getHost());
        props.put("mail.smtp.socketFactory.port", emailConfig.getSmtpPort());
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", emailConfig.getSmtpPort());

        return Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("content@goldstardiana.com", "medusa333");
                    }
                });

    }
}
