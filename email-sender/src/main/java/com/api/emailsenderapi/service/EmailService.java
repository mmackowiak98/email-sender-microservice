package com.api.emailsenderapi.service;

import com.api.emailsenderapi.exceptions.EmailAlreadyExistsException;
import com.api.emailsenderapi.exceptions.EmailNotFoundException;
import com.api.emailsenderapi.exceptions.GlobalEmailException;
import com.api.emailsenderapi.messaging.MessageCreator;
import com.api.emailsenderapi.model.Email;
import com.api.emailsenderapi.model.EmailContent;
import com.api.emailsenderapi.model.dto.EmailDTO;
import com.api.emailsenderapi.repository.EmailRepository;
import com.api.emailsenderapi.utils.EmailMapper;
import com.api.emailsenderapi.utils.TransportDelegator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class EmailService {

    EmailRepository emailRepository;
    MessageCreator messageCreator;
    TransportDelegator transportDelegator;

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    public Email saveEmail(Email email) {
        if(emailRepository.findByEmailAddress(email.getEmailAddress()).isPresent()) {
            logger.warn("Error thrown in saveEmail method");
            throw new EmailAlreadyExistsException();
        }
        return emailRepository.save(email);
    }

    public EmailDTO getEmailByAddress(String email) {
        Email foundEmail = emailRepository.findByEmailAddress(email)
                .orElseThrow(EmailNotFoundException::new);
        return EmailMapper.INSTANCE.emailToEmailDTO(foundEmail);
    }

    public EmailDTO getEmailById(Long id) {
        Email foundEmail = emailRepository.findById(id)
                .orElseThrow(EmailNotFoundException::new);

        return EmailMapper.INSTANCE.emailToEmailDTO(foundEmail);
    }

    public boolean sendEmails(EmailContent emailContent) {

        for (Email email : getAllEmails()) {
            Message message = messageCreator.createMessage();
            try {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(email.getEmailAddress()));
                message.setSubject(emailContent.getSubject());
                message.setContent(emailContent.getContent(), "text/plain");
                transportDelegator.send(message);
                log.info("message {} sent to {}", message, email.getEmailAddress());
            } catch (MessagingException e) {
                logger.warn("Error thrown in sendEmails method");
                throw new GlobalEmailException(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }
        return true;
    }



    public EmailDTO updateEmail(Long id, String email) {
        Email foundEmail = emailRepository.findById(id)
                .orElseThrow(EmailNotFoundException::new);

        foundEmail.setEmailAddress(email);

        return EmailMapper.INSTANCE.emailToEmailDTO(emailRepository.save(foundEmail));
    }

    public void deleteById(Long id) {
        if(!emailRepository.findById(id).isPresent()) {
            logger.warn("Error thrown in deleteById method");
            throw new EmailNotFoundException();
        }
        emailRepository.deleteById(id);
    }

    private List<Email> getAllEmails() {
        return emailRepository.findAll();
    }


}
