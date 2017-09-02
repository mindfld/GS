package com.goldstardiana.gs.services;

import com.goldstardiana.gs.model.EmailConfig;
import com.goldstardiana.gs.model.EmailData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {
    private List<EmailData> unreadEmails;

    @Autowired
    private EmailConfig emailConfig;
    @Autowired
    private EmailDataService emailDataService;

    public List<EmailData> getUnreadEmails() {
        List<EmailData> emailDataList = emailDataService.receiveEmail(emailConfig.getHost(), emailConfig.getMailStoreType(), emailConfig.getLogin(), emailConfig.getPassword());
        return emailDataList;
    }




}
