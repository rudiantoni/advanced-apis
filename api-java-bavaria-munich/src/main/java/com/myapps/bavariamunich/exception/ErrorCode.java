package com.myapps.bavariamunich.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    UNAUTHORIZED(HttpStatus.UNAUTHORIZED.getReasonPhrase()),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()),
    PRODUCT_NOT_FOUND("Product not found"),
    MVC_REQUEST_BODY_MALFORMED("Request body is invalid or malformed JSON");

    private final String defaultMessage;

    ErrorCode(String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }
}
