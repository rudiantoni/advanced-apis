package com.myapps.bavariamunich.dto;

import java.util.Collections;
import java.util.List;

public class ErrorResponseDto {

    public String requestId;
    public List<ErrorItem> errors;

    public ErrorResponseDto(String requestId, List<ErrorItem> errors) {
        this.requestId = requestId;
        this.errors = errors;
    }

    public static ErrorResponseDto of(String requestId, ErrorItem error) {
        return new ErrorResponseDto(requestId, Collections.singletonList(error));
    }
}
