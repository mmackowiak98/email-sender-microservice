package com.api.emailsenderapi.exceptions;

import org.springframework.http.HttpStatus;

public class MessageException extends GlobalEmailException{
    public MessageException(String message, HttpStatus status) {
        super(message, status);
    }
}
