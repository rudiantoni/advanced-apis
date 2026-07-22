package com.myapps.bavariamunich.controller.base;

import com.myapps.bavariamunich.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;

public abstract class RequestBodyController {
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDto> handleMalformedJson(HttpMessageNotReadableException ex) {
        ErrorResponseDto result = ErrorResponseDto.of("Request body is invalid or malformed JSON");
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }
}
