package com.api.emailsenderapi.controller;

import com.api.emailsenderapi.model.Email;
import com.api.emailsenderapi.model.EmailContent;
import com.api.emailsenderapi.model.dto.EmailDTO;
import com.api.emailsenderapi.service.EmailService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Do osoby sprawadzającej zadanie! :D
 *
 * Ogólnie nie wydaje mi się, że to zadanie miało polegać
 * na stworzeniu poczty wysyłającej rzeczywiste maile przez SMTP
 * jednak po dopytaniu dostałem odpowiedź od HR, że ma być taka funkcja
 * A więc robiłem zgodnie z zaleceniem
 *
 */
@RestController
@RequestMapping("/api/v1/email")
@AllArgsConstructor
public class EmailController implements EmailControllerOperation {

    private static final Logger logger = LoggerFactory.getLogger(EmailController.class);
    private static final Marker REQUEST_MARKER = MarkerFactory.getMarker("REQUEST");

    EmailService emailService;

    @Override
    public Email saveEmail(Email email) {
        logger.info(REQUEST_MARKER,"POST Request to save email {}", email);
        return emailService.saveEmail(email);
    }

    @Override
    public EmailDTO findEmailByAddress(String email) {
        logger.info(REQUEST_MARKER,"GET Request to find email with address {}", email);
        return emailService.getEmailByAddress(email);
    }

    @Override
    public EmailDTO findEmailById(Long id) {
        logger.info(REQUEST_MARKER,"GET Request to find email with id {}", id);
        return emailService.getEmailById(id);
    }

    @Override
    public EmailDTO updateEmail(Long id, Email email) {
        logger.info(REQUEST_MARKER,"PUT Request to update email with id {}", id);
        return emailService.updateEmail(id,email.getEmailAddress());
    }

    @Override
    public void deleteEmail(Long id) {
        logger.info(REQUEST_MARKER,"DELETE Request to delete email with id {}", id);
        emailService.deleteById(id);
    }

    @Override
    public boolean sendEmails(EmailContent emailContent) {
        logger.info(REQUEST_MARKER,"POST Request to send email {}", emailContent);
        return emailService.sendEmails(emailContent);
    }
}
