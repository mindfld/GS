package com.goldstardiana.gs.services;

import com.goldstardiana.gs.model.EmailData;
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

    public List<EmailData> receiveEmail(String host, String user, String password) {
        List<EmailData> emailsBodyList = new LinkedList<>();

        try {

            Store store = configureEmailStore(host, user, password);
            store.connect(host, user, password);

            Folder emailFolder = store.getFolder("INBOX");
            Folder copyFolder = store.getFolder("INBOX.Processed");
            emailFolder.open(Folder.READ_WRITE);

            emailsBodyList = parseMessages(emailFolder, copyFolder);

            emailFolder.close(true);
            store.close();

        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }
        System.out.println("Retrieved " + emailsBodyList.size() + " email(s)");
        return emailsBodyList;
    }

    private List<EmailData> parseMessages(Folder emailFolder, Folder copyFolder) throws MessagingException, IOException {
        List<EmailData> emailList = new LinkedList<>();
        Message[] messages = emailFolder.getMessages();
        for (Message message : messages) {
            if (message.getSubject().contains("Success transaction")) {
                MimeMultipart content = (MimeMultipart) message.getContent();
                emailList.add(parseEmail(getTextFromMimeMultipart(content)));

            }
        }

        emailFolder.copyMessages(messages, copyFolder);

        for (Message message : messages) {
            message.setFlag(Flags.Flag.DELETED, true);
        }
        return emailList;
    }

    private EmailData parseEmail(String emailContent) {
        EmailData data = new EmailData();
        String[] lines = emailContent.split("\r\n");
        for (String line : lines) {
            if (line.contains("Email клиента:")) {
                data.setClientEmailAddress(line.substring(line.indexOf("Email клиента:") + 15, line.indexOf("Описание:")).trim());
            }
            if (line.contains("Описание:")) {
                data.setSelectedProduct(line.substring(line.indexOf("Описание:") + 10, line.indexOf("Номер карты/счета:")).trim());
            }
            if (line.contains("Номер заказа на сайте:")) {
                data.setOrderId(line.substring(line.indexOf("Номер заказа на сайте:") + 22, line.indexOf("Email клиента:")).trim());
            }
            if (line.contains("Сумма платежа:")) {
                data.setPrice(line.substring(line.indexOf("Сумма платежа:") + 15, line.indexOf("Дата платежа:")).trim());
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
                result = result + "\n" + Jsoup.parse(html).text();
            } else if (bodyPart.getContent() instanceof MimeMultipart) {
                result = result + bodyPart.getContent();
            }
        }
        return result;
    }

    private Store configureEmailStore(String host, String user, String password) throws NoSuchProviderException {
        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "imaps");
        Session session = Session.getDefaultInstance(props, null);
        return session.getStore("imaps");
    }
}
