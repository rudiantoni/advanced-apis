package com.myapps.bavariamunich.service.validation;

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

@Service
public class UserValidationService {

    private static final Logger logger = LoggerFactory.getLogger(UserValidationService.class);
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

        ValidationUtil.checkNotNullOrEmpty(given.getEmail(), errors, "email");
        ValidationUtil.checkNotNullOrEmpty(given.getUsername(), errors, "username");
        ValidationUtil.checkNotNullOrEmpty(given.getPassword(), errors, "password");

        if (given.getEmail() != null) {
            ValidationUtil.checkLengthNotGreaterThan(given.getEmail(), 128, errors, "email");
            ValidationUtil.checkUnique(existsByEmail(given.getEmail()), errors, "email");
        }
        if (given.getUsername() != null) {
            ValidationUtil.checkLengthNotGreaterThan(given.getUsername(), 128, errors, "username");
        }
        if (given.getPassword() != null) {
            ValidationUtil.checkLengthNotGreaterThan(given.getPassword(), 512, errors, "password");
        }
        if (!errors.isEmpty()) {
            String errMsg = String.format(
                    "Unable to create user with email: '%s' and username: '%s'",
                    given.getEmail(),
                    given.getUsername()
            );
            logger.warn(errMsg);
            errors.add(0, errMsg);
            throw new MultiErrorException(HttpStatus.BAD_REQUEST, errors);
        }

        throwIfErrors(errors, errorMsg);
    }

    private void throwIfErrors(ArrayList<String> errors, String errorMsg) {
        if (!errors.isEmpty()) {
            logger.warn(errorMsg);
            errors.add(0, errorMsg);
            throw new MultiErrorException(HttpStatus.BAD_REQUEST, errors);
        }
    }

}
