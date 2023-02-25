package com.api.emailsenderapi.messaging;

import com.api.emailsenderapi.config.SMTPConfig;
import com.api.emailsenderapi.config.SMTPConfigProperties;
import com.api.emailsenderapi.exceptions.MessageException;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MessageCreator {

    SMTPConfigProperties config;
    SMTPConfig smtpConfig;

    public Message createMessage() {
        Message message = new MimeMessage(smtpConfig.createSession());
        try {
            message.setFrom(new InternetAddress(config.getSmtpConfig().get("emailAddress")));
        } catch (MessagingException e) {
            throw new MessageException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return message;
    }


}
