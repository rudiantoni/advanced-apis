package com.myapps.bavariamunich.dto;

import com.myapps.bavariamunich.exception.ErrorCode;

import java.util.Map;

public class ErrorItem {
    public String code;
    public String message;
    public String field;
    public Map<String, Object> params;

    public ErrorItem() {
    }

    public ErrorItem(String code, String message, String field, Map<String, Object> params) {
        this.code = code;
        this.message = message;
        this.field = field;
        this.params = params;
    }

    public static ErrorItem of(ErrorCode errorCode) {
        return new ErrorItem(errorCode.name(), errorCode.getDefaultMessage(), null, null);
    }

    public static ErrorItem of(ErrorCode errorCode, String message) {
        return new ErrorItem(errorCode.name(), message, null, null);
    }

    public static ErrorItem of(ErrorCode errorCode, String message, String field, Map<String, Object> params) {
        return new ErrorItem(errorCode.name(), message, field, params);
    }

}
