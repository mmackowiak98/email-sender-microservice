package com.api.emailsenderapi;

import com.api.emailsenderapi.exceptions.EmailAlreadyExistsException;
import com.api.emailsenderapi.exceptions.EmailNotFoundException;
import com.api.emailsenderapi.messaging.MessageCreator;
import com.api.emailsenderapi.model.Email;
import com.api.emailsenderapi.model.EmailContent;
import com.api.emailsenderapi.model.dto.EmailDTO;
import com.api.emailsenderapi.repository.EmailRepository;
import com.api.emailsenderapi.service.EmailService;
import com.api.emailsenderapi.utils.TransportDelegator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @Mock
    private EmailRepository emailRepository;

    @Mock
    private MessageCreator messageCreator;

    @Mock
    private TransportDelegator transportDelegator;

    @InjectMocks
    private EmailService emailService;

    @Test
    void saveEmail_shouldSaveNewEmail_whenEmailDoesNotExist() {
        Email email = new Email();
        email.setEmailAddress("test@example.com");

        when(emailRepository.findByEmailAddress(email.getEmailAddress()))
                .thenReturn(Optional.empty());
        when(emailRepository.save(email))
                .thenReturn(email);

        Email result = emailService.saveEmail(email);

        assertNotNull(result);
        assertEquals(email.getEmailAddress(), result.getEmailAddress());

        verify(emailRepository).findByEmailAddress(email.getEmailAddress());
        verify(emailRepository).save(email);
    }

    @Test
    void saveEmail_shouldThrowEmailAlreadyExistsException_whenEmailExists() {
        Email email = new Email();
        email.setEmailAddress("test@example.com");

        when(emailRepository.findByEmailAddress(email.getEmailAddress()))
                .thenReturn(Optional.of(email));

        assertThrows(EmailAlreadyExistsException.class, () -> emailService.saveEmail(email));

        verify(emailRepository).findByEmailAddress(email.getEmailAddress());
    }

    @Test
    void getEmailByAddress_shouldReturnEmailDTO_whenEmailExists() {
        Email email = new Email();
        email.setEmailAddress("test@example.com");

        when(emailRepository.findByEmailAddress(email.getEmailAddress()))
                .thenReturn(Optional.of(email));

        EmailDTO result = emailService.getEmailByAddress(email.getEmailAddress());

        assertNotNull(result);
        assertEquals(email.getEmailAddress(), result.getEmailAddress());

        verify(emailRepository).findByEmailAddress(email.getEmailAddress());
    }

    @Test
    void getEmailByAddress_shouldThrowEmailNotFoundException_whenEmailDoesNotExist() {
        String email = "test@example.com";

        when(emailRepository.findByEmailAddress(email))
                .thenReturn(Optional.empty());

        assertThrows(EmailNotFoundException.class, () -> emailService.getEmailByAddress(email));

        verify(emailRepository).findByEmailAddress(email);
    }

    @Test
    void getEmailById_shouldReturnEmailDTO_whenEmailExists() {
        Long id = 1L;
        Email email = new Email();
        email.setEmailId(id);
        email.setEmailAddress("test@example.com");

        when(emailRepository.findById(id))
                .thenReturn(Optional.of(email));

        EmailDTO result = emailService.getEmailById(id);

        assertNotNull(result);
        assertEquals(email.getEmailAddress(), result.getEmailAddress());

        verify(emailRepository).findById(id);
    }

    @Test
    void getEmailById_shouldThrowEmailNotFoundException_whenEmailDoesNotExist() {
        Long id = 1L;

        when(emailRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(EmailNotFoundException.class, () -> emailService.getEmailById(id));

        verify(emailRepository).findById(id);
    }

    @Test
    void updateEmail_shouldUpdateEmail_whenEmailExists() {
        Long id = 1L;
        String email = "new-email@example.com";
        Email foundEmail = new Email();
        foundEmail.setEmailId(id);
        foundEmail.setEmailAddress("old-email@example.com");

        when(emailRepository.findById(id)).thenReturn(Optional.of(foundEmail));
        when(emailRepository.save(foundEmail)).thenReturn(foundEmail);

        EmailDTO updatedEmail = emailService.updateEmail(id, email);

        assertEquals(email, updatedEmail.getEmailAddress());
    }

    @Test
    void deleteById_shouldThrowEmailNotFoundException_whenEmailNotFound() {
        Long id = 1L;

        when(emailRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EmailNotFoundException.class, () -> emailService.deleteById(id));

        verify(emailRepository, never()).deleteById(id);
    }

    @Test
    void deleteById_shouldDeleteEmail_whenEmailExists() {
        Long id = 1L;
        Email foundEmail = new Email();
        foundEmail.setEmailId(id);
        foundEmail.setEmailAddress("email@example.com");

        when(emailRepository.findById(id)).thenReturn(Optional.of(foundEmail));

        emailService.deleteById(id);

        verify(emailRepository).deleteById(id);
    }

    @Test
    void testSendEmails() throws MessagingException {
        Email email1 = new Email();
        email1.setEmailAddress("test1@example.com");
        Email email2 = new Email();
        email2.setEmailAddress("test2@example.com");
        EmailContent emailContent = new EmailContent();
        emailContent.setSubject("Test Subject");
        emailContent.setContent("Test Content");

        Message message = mock(Message.class);

        when(emailRepository.findAll()).thenReturn(List.of(email1, email2));
        when(messageCreator.createMessage()).thenReturn(message);

        doNothing().when(transportDelegator).send(message);
        boolean result = emailService.sendEmails(emailContent);

        assertTrue(result);
        verify(emailRepository, times(1)).findAll();
        verify(messageCreator, times(2)).createMessage();
        verify(transportDelegator, times(2)).send(message);
    }

}
