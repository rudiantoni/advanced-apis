package com.myapps.bavariamunich.util;

import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Objects;

public class ValidationUtil {
    private ValidationUtil() {
    }

    private static BigDecimal maxForNumeric(int precision, int scale) {
        return BigDecimal.TEN.pow(precision - scale).subtract(BigDecimal.ONE.scaleByPowerOfTen(-scale));
    }

    public static boolean isNull(Object value) {
        return value == null;
    }

    public static boolean isNullOrEmpty(String value) {
        return value == null || isEmpty(value);
    }

    public static boolean isDefined(JsonNullable<?> value) {
        return value != null && !value.isUndefined();
    }

    public static boolean isEmpty(@NonNull String value) {
        Objects.requireNonNull(value, "value");
        return value.isEmpty();
    }

    public static boolean isLengthGreaterThan(@NonNull String value, int maxLength) {
        Objects.requireNonNull(value, "value");
        return value.length() > maxLength;
    }

    public static boolean isPositive(@NonNull BigDecimal value) {
        Objects.requireNonNull(value, "value");
        return value.compareTo(BigDecimal.ZERO) > 0;
    }

    public static boolean isNegative(@NonNull Integer value) {
        Objects.requireNonNull(value, "value");
        return value < 0;
    }

    public static void checkNotNull(
            BigDecimal value,
            @NonNull ArrayList<String> errorList,
            @NonNull String fieldName
    ) {
        Objects.requireNonNull(errorList, "errorList");
        Objects.requireNonNull(fieldName, "fieldName");
        if (isNull(value)) {
            errorList.add(String.format("The field %s cannot be null.", fieldName));
        }
    }

    public static void checkNotNullOrEmpty(
            String value,
            @NonNull ArrayList<String> errorList,
            @NonNull String fieldName
    ) {
        Objects.requireNonNull(errorList, "errorList");
        Objects.requireNonNull(fieldName, "fieldName");
        if (isNullOrEmpty(value)) {
            errorList.add(String.format("The field %s cannot be null or empty.", fieldName));
        }
    }

    public static void checkDefinedNotNull(
            JsonNullable<?> value,
            @NonNull ArrayList<String> errorList,
            @NonNull String fieldName
    ) {
        Objects.requireNonNull(errorList, "errorList");
        Objects.requireNonNull(fieldName, "fieldName");
        if (isDefined(value) && isNull(value.get())) {
            errorList.add(String.format("The field %s cannot be explicitly null.", fieldName));
        }
    }

    public static void checkNumericPrecisionAndScale(
            BigDecimal value,
            int precision,
            int scale,
            @NonNull ArrayList<String> errorList,
            @NonNull String fieldName
    ) {
        Objects.requireNonNull(errorList, "errorList");
        Objects.requireNonNull(fieldName, "fieldName");
        if (value == null) {
            return;
        }
        if (precision <= 0 || scale < 0 || scale > precision) {
            throw new IllegalArgumentException(String.format(
                    "Invalid numeric precision=%d scale=%d", precision, scale));
        }
        BigDecimal normalized = value.stripTrailingZeros();
        int actualScale = Math.max(normalized.scale(), 0);
        if (actualScale > scale) {
            errorList.add(String.format(
                    "The field %s cannot have more than %d decimal places.", fieldName, scale));
        }
        BigDecimal max = maxForNumeric(precision, scale);
        if (normalized.abs().compareTo(max) > 0) {
            errorList.add(String.format(
                    "The field %s cannot exceed %s.", fieldName, max.toPlainString()));
        }
    }

    public static void checkNotEmpty(
            @NonNull String value,
            @NonNull ArrayList<String> errorList,
            @NonNull String fieldName
    ) {
        Objects.requireNonNull(value, "value");
        Objects.requireNonNull(errorList, "errorList");
        Objects.requireNonNull(fieldName, "fieldName");
        if (isEmpty(value)) {
            errorList.add(String.format("The field %s cannot be empty.", fieldName));
        }
    }

    public static void checkLengthNotGreaterThan(
            @NonNull String value,
            int maxLength,
            @NonNull ArrayList<String> errorList,
            @NonNull String fieldName
    ) {
        Objects.requireNonNull(value, "value");
        Objects.requireNonNull(errorList, "errorList");
        Objects.requireNonNull(fieldName, "fieldName");
        if (isLengthGreaterThan(value, maxLength)) {
            errorList.add(String.format("The field %s cannot exceed %d characters.", fieldName, maxLength));
        }
    }

    public static void checkUnique(
            boolean exists,
            @NonNull ArrayList<String> errorList,
            @NonNull String fieldName
    ) {
        Objects.requireNonNull(errorList, "errorList");
        Objects.requireNonNull(fieldName, "fieldName");
        if (exists) {
            errorList.add(String.format("The field %s must be unique.", fieldName));
        }
    }

    public static void checkPositive(
            @NonNull BigDecimal value,
            @NonNull ArrayList<String> errorList,
            @NonNull String fieldName
    ) {
        Objects.requireNonNull(value, "value");
        Objects.requireNonNull(errorList, "errorList");
        Objects.requireNonNull(fieldName, "fieldName");
        if (!isPositive(value)) {
            errorList.add(String.format("The field %s must be positive.", fieldName));
        }
    }

    public static void checkNotNegative(
            @NonNull Integer value,
            @NonNull ArrayList<String> errorList,
            @NonNull String fieldName
    ) {
        Objects.requireNonNull(value, "value");
        Objects.requireNonNull(errorList, "errorList");
        Objects.requireNonNull(fieldName, "fieldName");
        if (isNegative(value)) {
            errorList.add(String.format("The field %s cannot be negative.", fieldName));
        }
    }

}
