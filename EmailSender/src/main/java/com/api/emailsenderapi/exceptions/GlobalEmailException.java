package com.api.emailsenderapi.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class GlobalEmailException extends RuntimeException {
    private String message;
    private HttpStatus status;

    public GlobalEmailException(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

}
