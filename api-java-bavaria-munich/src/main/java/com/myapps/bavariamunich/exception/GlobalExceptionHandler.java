package com.myapps.bavariamunich.exception;

import com.myapps.bavariamunich.config.AppConsts;
import com.myapps.bavariamunich.dto.ErrorItem;
import com.myapps.bavariamunich.dto.ErrorResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Optional;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MultiErrorException.class)
    public ResponseEntity<ErrorResponseDto> handleMultiError(MultiErrorException ex) {
        ErrorResponseDto result = new ErrorResponseDto(currentRequestId(), ex.getErrors());
        return new ResponseEntity<>(result, ex.getStatus());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDto> handleHttpMesageNotReadable(HttpMessageNotReadableException ex) {
        ErrorResponseDto result = ErrorResponseDto.of(
                currentRequestId(),
                ErrorItem.of(ErrorCode.MVC_REQUEST_BODY_MALFORMED)
        );
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGeneric(Exception ex) {
        logger.error("Unhandled exception", ex);
        ErrorResponseDto result = ErrorResponseDto.of(
                currentRequestId(),
                ErrorItem.of(ErrorCode.INTERNAL_SERVER_ERROR)
        );
        return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private static String currentRequestId() {
        return Optional.ofNullable(MDC.get(AppConsts.MDC_REQUEST_ID_KEY))
                .orElse(AppConsts.UNKNOWN_REQUEST_ID);
    }
}
