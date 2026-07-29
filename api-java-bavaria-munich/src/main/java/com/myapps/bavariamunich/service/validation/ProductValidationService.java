package com.myapps.bavariamunich.service.validation;

import com.myapps.bavariamunich.dto.ErrorItem;
import com.myapps.bavariamunich.dto.ProductFullRequestDto;
import com.myapps.bavariamunich.dto.ProductPartialRequestDto;
import com.myapps.bavariamunich.exception.MultiErrorException;
import com.myapps.bavariamunich.util.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductValidationService {
    private static final Logger logger = LoggerFactory.getLogger(ProductValidationService.class);
    private static final int NAME_MAX_LENGTH = 256;
    private static final int DESCRIPTION_MAX_LENGTH = 512;
    private static final int REFERENCE_MAX_LENGTH = 128;
    private static final int IMAGE_URL_MAX_LENGTH = 1024;
    private static final int PRICE_PRECISION = 19;
    private static final int PRICE_SCALE = 2;

    public void validateFull(ProductFullRequestDto given, String errorMsg) {
        ArrayList<String> errors = new ArrayList<>();
        validateFieldName(given.getName(), errors);
        validateFieldDescription(given.getDescription(), errors);
        validateFieldPrice(given.getPrice(), errors);
        validateFieldReference(given.getReference(), errors);
        validateFieldStockQuantity(given.getStockQuantity(), errors);
        validateFieldImageUrl(given.getImageUrl(), errors);
        throwIfErrors(errors, errorMsg);
    }

    public void validatePartial(ProductPartialRequestDto given, String errorMsg) {
        ArrayList<String> errors = new ArrayList<>();
        if (ValidationUtil.isDefined(given.getName())) {
            ValidationUtil.checkDefinedNotNull(given.getName(), errors, "name");
            if (given.getName().get() != null) {
                validateFieldName(given.getName().get(), errors);
            }
        }
        if (ValidationUtil.isDefined(given.getDescription())) {
            ValidationUtil.checkDefinedNotNull(given.getDescription(), errors, "description");
            if (given.getDescription().get() != null) {
                validateFieldDescription(given.getDescription().get(), errors);
            }
        }
        if (ValidationUtil.isDefined(given.getPrice())) {
            ValidationUtil.checkDefinedNotNull(given.getPrice(), errors, "price");
            if (given.getPrice().get() != null) {
                validateFieldPrice(given.getPrice().get(), errors);
            }
        }
        if (ValidationUtil.isDefined(given.getReference())) {
            validateFieldReference(given.getReference().get(), errors);
        }
        if (ValidationUtil.isDefined(given.getStockQuantity())) {
            validateFieldStockQuantity(given.getStockQuantity().get(), errors);
        }
        if (ValidationUtil.isDefined(given.getImageUrl())) {
            validateFieldImageUrl(given.getImageUrl().get(), errors);
        }
        throwIfErrors(errors, errorMsg);
    }

    private void validateFieldName(String name, ArrayList<String> errors) {
        ValidationUtil.checkNotNullOrEmpty(name, errors, "name");
        if (!ValidationUtil.isNullOrEmpty(name)) {
            ValidationUtil.checkLengthNotGreaterThan(name, NAME_MAX_LENGTH, errors, "name");
        }
    }

    private void validateFieldDescription(String description, ArrayList<String> errors) {
        ValidationUtil.checkNotNullOrEmpty(description, errors, "description");
        if (!ValidationUtil.isNullOrEmpty(description)) {
            ValidationUtil.checkLengthNotGreaterThan(description, DESCRIPTION_MAX_LENGTH, errors, "description");
        }
    }

    private void validateFieldPrice(BigDecimal price, ArrayList<String> errors) {
        ValidationUtil.checkNotNull(price, errors, "price");
        if (!ValidationUtil.isNull(price)) {
            ValidationUtil.checkPositive(price, errors, "price");
            ValidationUtil.checkNumericPrecisionAndScale(price, PRICE_PRECISION, PRICE_SCALE, errors, "price");
        }
    }

    private void validateFieldReference(String reference, ArrayList<String> errors) {
        if (ValidationUtil.isNull(reference)) {
            return;
        }
        ValidationUtil.checkNotEmpty(reference, errors, "reference");
        ValidationUtil.checkLengthNotGreaterThan(reference, REFERENCE_MAX_LENGTH, errors, "reference");
    }

    private void validateFieldStockQuantity(Integer stockQuantity, ArrayList<String> errors) {
        if (ValidationUtil.isNull(stockQuantity)) {
            return;
        }
        ValidationUtil.checkNotNegative(stockQuantity, errors, "stockQuantity");
    }

    private void validateFieldImageUrl(String imageUrl, ArrayList<String> errors) {
        if (ValidationUtil.isNull(imageUrl)) {
            return;
        }
        ValidationUtil.checkNotEmpty(imageUrl, errors, "imageUrl");
        ValidationUtil.checkLengthNotGreaterThan(imageUrl, IMAGE_URL_MAX_LENGTH, errors, "imageUrl");
    }

    private void throwIfErrors(ArrayList<String> errors, String errorMsg) {
        if (!errors.isEmpty()) {
            logger.warn(errorMsg);
            errors.add(0, errorMsg);
            List<ErrorItem> items = errors.stream()
                    .map(it -> new ErrorItem("UNCLASSIFIED", it, null, null))
                    .collect(Collectors.toList());
            throw new MultiErrorException(HttpStatus.BAD_REQUEST, items);
        }
    }

}
