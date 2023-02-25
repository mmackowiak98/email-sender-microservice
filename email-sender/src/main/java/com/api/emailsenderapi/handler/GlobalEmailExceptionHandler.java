package com.api.emailsenderapi.handler;

import com.api.emailsenderapi.exceptions.GlobalEmailException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalEmailExceptionHandler {

    @ExceptionHandler(GlobalEmailException.class)
    public ProblemDetail handleGlobalMailException(GlobalEmailException e) {
        return ProblemDetail.forStatusAndDetail(e.getStatus(),e.getMessage());
    }

    @ExceptionHandler(Throwable.class)
    public ProblemDetail handleUnknownException(Throwable e){
        return ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(500),e.getMessage());
    }

}
