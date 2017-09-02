package com.goldstardiana.gs.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:email.properties")
@ConfigurationProperties
public class EmailConfig {

    private String host;
    private String mailStoreType;
    private String login;
    private String password;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getMailStoreType() {
        return mailStoreType;
    }

    public void setMailStoreType(String mailStoreType) {
        this.mailStoreType = mailStoreType;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
