package com.api.emailsenderapi.exceptions;

import org.springframework.http.HttpStatus;

public class EmailAlreadyExistsException extends GlobalEmailException {
    public EmailAlreadyExistsException() {
        super("Email already exists", HttpStatus.BAD_REQUEST);
    }
}
