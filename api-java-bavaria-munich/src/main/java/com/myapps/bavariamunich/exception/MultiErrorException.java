package com.myapps.bavariamunich.exception;

import com.myapps.bavariamunich.dto.ErrorItem;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class MultiErrorException extends RuntimeException {
    private final HttpStatus status;
    private final List<ErrorItem> errors;

    public MultiErrorException(HttpStatus status, List<ErrorItem> errors) {
        this.status = status;
        this.errors = errors;
    }

    public MultiErrorException(HttpStatus status, ErrorCode code) {
        this(status, Collections.singletonList(
                ErrorItem.of(code)
        ));
    }

    public MultiErrorException(HttpStatus status, ErrorCode code, String message) {
        this(status, Collections.singletonList(
                ErrorItem.of(code, message)
        ));
    }

    public MultiErrorException(
            HttpStatus status,
            ErrorCode code,
            String message,
            String field,
            Map<String, Object> params
    ) {
        this(status, Collections.singletonList(
                ErrorItem.of(code, message, field, params)
        ));
    }

    public HttpStatus getStatus() {
        return status;
    }

    public List<ErrorItem> getErrors() {
        return errors;
    }
}
