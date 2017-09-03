package com.goldstardiana.gs.services;

import com.goldstardiana.gs.model.EmailData;
import com.sun.mail.pop3.POP3Store;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

@Service
public class EmailDataService {

    public List<EmailData> receiveEmail(String host, String storeType, String user, String password) {
        List<EmailData> emailsBodyList = new LinkedList<>();

        try {

            Properties props = new Properties();
            props.setProperty("mail.store.protocol", "imaps");
            Session session = Session.getDefaultInstance(props, null);
            Store store = session.getStore("imaps");
            store.connect(host, user, password);

            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_WRITE);

            //4) retrieve the messages from the folder in an array and print it
            Message[] messages = emailFolder.getMessages();
            for (int i = 0; i < messages.length; i++) {
                Message message = messages[i];
                if (message.getSubject().contains("Success transaction")) {
                    MimeMultipart content = (MimeMultipart) message.getContent();
                    emailsBodyList.add(parseEmail(getTextFromMimeMultipart(content)));

                }
            }
            Folder copyFolder = store.getFolder("INBOX.Processed");
            emailFolder.copyMessages(messages, copyFolder);

            for (Message message : messages) {
                message.setFlag(Flags.Flag.DELETED, true);
            }

            //5) close the store and folder objects
            emailFolder.close(true);
            store.close();

        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }
        System.out.println("Retrieved " + emailsBodyList.size() + " email(s)");
        return emailsBodyList;
    }

    private EmailData parseEmail(String emailContent) {
        EmailData data = new EmailData();
        String[] lines = emailContent.split("\r\n");
        for (String line : lines) {
            if (line.contains("Email клиента:")) {
                data.setClientEmailAddress(line.substring(line.indexOf(":") + 1).trim());
            }
            if (line.contains("Описание:")) {
                data.setSelectedProduct(line.substring(line.indexOf(":") + 1).trim());
            }
            if (line.contains("Номер заказа на сайте:")) {
                data.setOrderId(line.substring(line.indexOf(":") + 1).trim());
            }
            if (line.contains("Сумма платежа:")) {
                data.setPrice(line.substring(line.indexOf(":") + 1).trim());
            }
        }
        return data;
    }


    private String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws MessagingException, IOException {
        String result = "";
        int count = mimeMultipart.getCount();
        for (int i = 0; i < count; i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            if (bodyPart.isMimeType("text/plain")) {
                result = result + "\n" + bodyPart.getContent();
            } else if (bodyPart.isMimeType("text/html")) {
                String html = (String) bodyPart.getContent();
                result = result + "\n" +
                .text();
            } else if (bodyPart.getContent() instanceof MimeMultipart) {
                result = result + bodyPart.getContent();
            }
        }
        return result;
    }
}
