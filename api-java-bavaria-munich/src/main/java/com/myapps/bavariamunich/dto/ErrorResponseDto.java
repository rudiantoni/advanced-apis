package com.myapps.bavariamunich.dto;

import java.util.Collections;
import java.util.List;

public class ErrorResponseDto {
    public List<String> errors;

    public ErrorResponseDto(List<String> errors) {
        this.errors = errors;
    }

    public static ErrorResponseDto of(String error) {
        return new ErrorResponseDto(Collections.singletonList(error));
    }
}
