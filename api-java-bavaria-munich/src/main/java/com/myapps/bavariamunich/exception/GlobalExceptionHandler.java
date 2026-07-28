package com.myapps.bavariamunich.exception;

import com.myapps.bavariamunich.dto.ErrorResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MultiErrorException.class)
    public ResponseEntity<ErrorResponseDto> handleMultiError(MultiErrorException ex) {
        return new ResponseEntity<>(new ErrorResponseDto(ex.getErrors()), ex.getStatus());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDto> handleHttpMesageNotReadable(HttpMessageNotReadableException ex) {
        ErrorResponseDto result = ErrorResponseDto.of("Request body is invalid or malformed JSON");
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGeneric(Exception ex) {
        logger.error("Unhandled exception", ex);
        return new ResponseEntity<>(
                ErrorResponseDto.of(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
