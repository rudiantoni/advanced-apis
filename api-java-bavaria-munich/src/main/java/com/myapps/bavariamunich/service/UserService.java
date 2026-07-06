package com.myapps.bavariamunich.service;

import com.myapps.bavariamunich.definition.UserInternalDefinition;
import com.myapps.bavariamunich.dto.UserSecureDto;
import com.myapps.bavariamunich.dto.UserWriteDto;
import com.myapps.bavariamunich.entity.User;
import com.myapps.bavariamunich.mapper.UserMapper;
import com.myapps.bavariamunich.repository.UserRepository;
import com.myapps.bavariamunich.util.NormalizeUtil;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordService passwordService;

    public UserService(
            UserRepository userRepository,
            PasswordService passwordService
    ) {
        this.userRepository = userRepository;
        this.passwordService = passwordService;
    }

    public UserSecureDto create(UserWriteDto given) {
        String hashedPassword = passwordService.hash(given.getPassword());
        String normalizedEmail = NormalizeUtil.normalizeEmail(given.getEmail());
        UserWriteDto normalized = new UserWriteDto(normalizedEmail, given.getUsername(), hashedPassword);
        User created = userRepository.save(UserMapper.toEntity(normalized));
        return UserMapper.toSecureDto(created);
    }

    public Optional<UserInternalDefinition> searchByEmail(String email) {
        String normalizedEmail = NormalizeUtil.normalizeEmail(email);
        return userRepository.getByEmail(normalizedEmail).map(UserMapper::toInternalDefinition);
    }
}
