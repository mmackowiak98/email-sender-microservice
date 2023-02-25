package com.api.emailsenderapi.config;


import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Properties;

@AllArgsConstructor
@Component
public class SMTPConfig {

    SMTPConfigProperties config;

    public Session createSession() {
        return Session.getInstance(getSmtpProperties(), new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(config.getSmtpConfig().get("emailAddress"), config.getSmtpConfig().get("password"));
            }
        });
    }
    private Properties getSmtpProperties() {
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", config.getSmtpConfig().get("host"));
        properties.put("mail.smtp.port", config.getSmtpConfig().get("port"));
        properties.put("mail.smtp.auth", config.getSmtpConfig().get("flag"));
        properties.put("mail.smtp.starttls.enable", config.getSmtpConfig().get("flag"));
        return properties;
    }
}
