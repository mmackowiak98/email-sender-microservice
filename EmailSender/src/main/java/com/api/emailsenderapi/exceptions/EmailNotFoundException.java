package com.api.emailsenderapi.exceptions;

import org.springframework.http.HttpStatus;

public class EmailNotFoundException extends GlobalEmailException {
    public EmailNotFoundException() {
        super("No such Email found", HttpStatus.NOT_FOUND);
    }
}
