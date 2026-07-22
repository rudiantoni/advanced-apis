package com.myapps.bavariamunich.service;

import com.myapps.bavariamunich.definition.UserInternalDefinition;
import com.myapps.bavariamunich.dto.UserFullRequestDto;
import com.myapps.bavariamunich.dto.UserSecureResponseDto;
import com.myapps.bavariamunich.entity.User;
import com.myapps.bavariamunich.mapper.UserMapper;
import com.myapps.bavariamunich.repository.UserRepository;
import com.myapps.bavariamunich.service.validation.UserValidationService;
import com.myapps.bavariamunich.util.NormalizeUtil;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserValidationService userValidationService;
    private final PasswordService passwordService;

    public UserService(
            UserRepository userRepository,
            UserValidationService userValidationService,
            PasswordService passwordService
    ) {
        this.userRepository = userRepository;
        this.userValidationService = userValidationService;
        this.passwordService = passwordService;
    }

    public UserSecureResponseDto create(UserFullRequestDto given) {
        userValidationService.validateFull(given, String.format(
                "Unable to create user with email: '%s' and username: '%s'", given.getEmail(), given.getUsername()
        ));
        String hashedPassword = passwordService.hash(given.getPassword());
        String normalizedEmail = NormalizeUtil.normalizeEmail(given.getEmail());
        UserFullRequestDto normalized = new UserFullRequestDto(normalizedEmail, given.getUsername(), hashedPassword);
        User created = userRepository.save(UserMapper.toEntity(normalized));
        return UserMapper.toSecureDto(created);
    }

    public Optional<UserInternalDefinition> searchByEmail(String email) {
        String normalizedEmail = NormalizeUtil.normalizeEmail(email);
        return userRepository.getByEmail(normalizedEmail).map(UserMapper::toInternalDefinition);
    }

}
