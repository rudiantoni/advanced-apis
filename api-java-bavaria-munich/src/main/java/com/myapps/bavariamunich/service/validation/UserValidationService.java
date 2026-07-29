package com.myapps.bavariamunich.service.validation;

import com.myapps.bavariamunich.dto.ErrorItem;
import com.myapps.bavariamunich.dto.UserFullRequestDto;
import com.myapps.bavariamunich.exception.MultiErrorException;
import com.myapps.bavariamunich.repository.UserRepository;
import com.myapps.bavariamunich.util.NormalizeUtil;
import com.myapps.bavariamunich.util.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserValidationService {

    private static final Logger logger = LoggerFactory.getLogger(UserValidationService.class);
    private static final int EMAIL_MAX_LENGTH = 128;
    private static final int USERNAME_MAX_LENGTH = 128;
    private static final int PASSWORD_MAX_LENGTH = 512;
    private final UserRepository userRepository;

    public UserValidationService(
            UserRepository userRepository
    ) {
        this.userRepository = userRepository;
    }

    private boolean existsByEmail(String email) {
        String normalizedEmail = NormalizeUtil.normalizeEmail(email);
        return userRepository.existsByEmail(normalizedEmail);
    }

    public void validateFull(UserFullRequestDto given, String errorMsg) {
        ArrayList<String> errors = new ArrayList<>();
        validateFieldEmail(given.getEmail(), errors);
        validateFieldUsername(given.getUsername(), errors);
        validateFieldPassword(given.getPassword(), errors);
        throwIfErrors(errors, errorMsg);
    }

    private void validateFieldEmail(String email, ArrayList<String> errors) {
        ValidationUtil.checkNotNullOrEmpty(email, errors, "email");
        if (!ValidationUtil.isNullOrEmpty(email)) {
            ValidationUtil.checkLengthNotGreaterThan(email, EMAIL_MAX_LENGTH, errors, "email");
            ValidationUtil.checkUnique(existsByEmail(email), errors, "email");
        }
    }

    private void validateFieldUsername(String username, ArrayList<String> errors) {
        ValidationUtil.checkNotNullOrEmpty(username, errors, "username");
        if (!ValidationUtil.isNullOrEmpty(username)) {
            ValidationUtil.checkLengthNotGreaterThan(username, USERNAME_MAX_LENGTH, errors, "username");
        }
    }

    private void validateFieldPassword(String password, ArrayList<String> errors) {
        ValidationUtil.checkNotNullOrEmpty(password, errors, "password");
        if (!ValidationUtil.isNullOrEmpty(password)) {
            ValidationUtil.checkLengthNotGreaterThan(password, PASSWORD_MAX_LENGTH, errors, "password");
        }
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
