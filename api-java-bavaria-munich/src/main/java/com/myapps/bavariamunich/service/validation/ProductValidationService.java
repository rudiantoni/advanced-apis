package com.myapps.bavariamunich.service.validation;

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
        validateNameValue(given.getName(), errors);
        validateDescriptionValue(given.getDescription(), errors);
        validatePriceValue(given.getPrice(), errors);
        validateReferenceValue(given.getReference(), errors);
        validateStockQuantityValue(given.getStockQuantity(), errors);
        validateImageUrlValue(given.getImageUrl(), errors);
        throwIfErrors(errors, errorMsg);
    }

    public void validatePartial(ProductPartialRequestDto given, String errorMsg) {
        ArrayList<String> errors = new ArrayList<>();
        if (ValidationUtil.isDefined(given.getName())) {
            ValidationUtil.checkDefinedNotNull(given.getName(), errors, "name");
            if (given.getName().get() != null) {
                validateNameValue(given.getName().get(), errors);
            }
        }
        if (ValidationUtil.isDefined(given.getDescription())) {
            ValidationUtil.checkDefinedNotNull(given.getDescription(), errors, "description");
            if (given.getDescription().get() != null) {
                validateDescriptionValue(given.getDescription().get(), errors);
            }
        }
        if (ValidationUtil.isDefined(given.getPrice())) {
            ValidationUtil.checkDefinedNotNull(given.getPrice(), errors, "price");
            if (given.getPrice().get() != null) {
                validatePriceValue(given.getPrice().get(), errors);
            }
        }
        if (ValidationUtil.isDefined(given.getReference())) {
            validateReferenceValue(given.getReference().get(), errors);
        }
        if (ValidationUtil.isDefined(given.getStockQuantity())) {
            validateStockQuantityValue(given.getStockQuantity().get(), errors);
        }
        if (ValidationUtil.isDefined(given.getImageUrl())) {
            validateImageUrlValue(given.getImageUrl().get(), errors);
        }
        throwIfErrors(errors, errorMsg);
    }

    private void validateNameValue(String name, ArrayList<String> errors) {
        ValidationUtil.checkNotNullOrEmpty(name, errors, "name");
        if (name != null) {
            ValidationUtil.checkLengthNotGreaterThan(name, NAME_MAX_LENGTH, errors, "name");
        }
    }

    private void validateDescriptionValue(String description, ArrayList<String> errors) {
        ValidationUtil.checkNotNullOrEmpty(description, errors, "description");
        if (description != null) {
            ValidationUtil.checkLengthNotGreaterThan(description, DESCRIPTION_MAX_LENGTH, errors, "description");
        }
    }

    private void validatePriceValue(BigDecimal price, ArrayList<String> errors) {
        ValidationUtil.checkNotNull(price, errors, "price");
        if (price != null) {
            ValidationUtil.checkPositive(price, errors, "price");
            ValidationUtil.checkNumericPrecisionAndScale(price, PRICE_PRECISION, PRICE_SCALE, errors, "price");
        }
    }

    private void validateReferenceValue(String reference, ArrayList<String> errors) {
        if (reference == null) {
            return;
        }
        ValidationUtil.checkNotEmpty(reference, errors, "reference");
        ValidationUtil.checkLengthNotGreaterThan(reference, REFERENCE_MAX_LENGTH, errors, "reference");
    }

    private void validateStockQuantityValue(Integer stockQuantity, ArrayList<String> errors) {
        if (stockQuantity == null) {
            return;
        }
        ValidationUtil.checkNotNegative(stockQuantity, errors, "stockQuantity");
    }

    private void validateImageUrlValue(String imageUrl, ArrayList<String> errors) {
        if (imageUrl == null) {
            return;
        }
        ValidationUtil.checkNotEmpty(imageUrl, errors, "imageUrl");
        ValidationUtil.checkLengthNotGreaterThan(imageUrl, IMAGE_URL_MAX_LENGTH, errors, "imageUrl");
    }

    private void throwIfErrors(ArrayList<String> errors, String errorMsg) {
        if (!errors.isEmpty()) {
            logger.warn(errorMsg);
            errors.add(0, errorMsg);
            throw new MultiErrorException(HttpStatus.BAD_REQUEST, errors);
        }
    }

}
