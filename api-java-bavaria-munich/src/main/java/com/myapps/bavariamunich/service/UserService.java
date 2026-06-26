package com.myapps.bavariamunich.service;

import com.myapps.bavariamunich.config.AppProperties;
import com.myapps.bavariamunich.definition.DefaultUserDefinition;
import com.myapps.bavariamunich.definition.UserInternalDefinition;
import com.myapps.bavariamunich.dto.UserSecureDto;
import com.myapps.bavariamunich.dto.UserWriteDto;
import com.myapps.bavariamunich.entity.User;
import com.myapps.bavariamunich.mapper.UserMapper;
import com.myapps.bavariamunich.repository.UserRepository;
import com.myapps.bavariamunich.util.NormalizeUtil;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserSecureDto create(UserWriteDto given) {
        UserWriteDto normalized = new UserWriteDto(
                NormalizeUtil.normalizeEmail(given.getEmail()),
                given.getUsername(),
                given.getPassword()
        );
        User created = userRepository.save(UserMapper.toEntity(normalized));
        return UserMapper.toSecureDto(created);
    }

    public Optional<UserInternalDefinition> searchByEmail(String email) {
        String normalizedEmail = NormalizeUtil.normalizeEmail(email);
        Optional<DefaultUserDefinition> defaultUser = AppProperties.getSecurityDefaultUsers().stream()
                .filter(it -> Objects.equals(it.getEmail(), normalizedEmail))
                .findFirst();

        if (defaultUser.isPresent()) {
            return Optional.of(UserMapper.toInternalDefinition(defaultUser.get()));
        }
        return userRepository.getByEmail(normalizedEmail).map(UserMapper::toInternalDefinition);
    }
}
