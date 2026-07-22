package com.myapps.bavariamunich.exception;

import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

public class MultiErrorException extends RuntimeException {
    private final HttpStatus status;
    private final List<String> errors;

    public MultiErrorException(HttpStatus status, String error) {
        this.status = status;
        this.errors = Collections.singletonList(error);
    }

    public HttpStatus getStatus() {
        return status;
    }

    public List<String> getErrors() {
        return errors;
    }
}
