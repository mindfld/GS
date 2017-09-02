package com.goldstardiana.gs.tasks;

import com.goldstardiana.gs.model.EmailData;
import com.goldstardiana.gs.services.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmailCheckTask {

    private static final Logger log = LoggerFactory.getLogger(EmailCheckTask.class);
    private static final int CHECK_INTERVAL_SEC = 5 * 1000;

    @Autowired
    private EmailService emailService = new EmailService();

    @Scheduled(fixedRate = CHECK_INTERVAL_SEC)
    public void reportCurrentTime() {
        List<EmailData> emails = emailService.getUnreadEmails();
        for (EmailData data :emails){
           // emailService.sendEmail(generateEmail(EmailData));
        }
       // emailService.cleanEmails();
    }
}