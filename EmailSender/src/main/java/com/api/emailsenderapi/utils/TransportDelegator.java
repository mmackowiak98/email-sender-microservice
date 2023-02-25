package com.api.emailsenderapi.utils;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Transport;
import org.springframework.stereotype.Component;

/**
 * Miałem problem ze zmockowanie statycznej methody Transport.send
 * Więc stworzyłem wrapper, który umożliwia mi łatwy mock
 */
@Component
public class TransportDelegator {
    public void send(Message msg) throws MessagingException {
        Transport.send(msg);
    }
}