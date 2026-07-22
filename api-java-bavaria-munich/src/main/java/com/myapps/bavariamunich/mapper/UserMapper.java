package com.myapps.bavariamunich.mapper;

import com.myapps.bavariamunich.definition.UserInternalDefinition;
import com.myapps.bavariamunich.dto.UserFullRequestDto;
import com.myapps.bavariamunich.dto.UserSecureResponseDto;
import com.myapps.bavariamunich.entity.User;

public class UserMapper {

    public static UserSecureResponseDto toSecureDto(User entity) {
        if (entity == null) {
            return null;
        }
        UserSecureResponseDto dto = new UserSecureResponseDto();
        dto.setId(entity.getId());
        dto.setEmail(entity.getEmail());
        dto.setUsername(entity.getUsername());
        return dto;
    }

    public static UserInternalDefinition toInternalDefinition(User entity) {
        if (entity == null) {
            return null;
        }
        return new UserInternalDefinition(
                entity.getId(), entity.getEmail(), entity.getUsername(), entity.getPassword()
        );
    }

    public static User toEntity(UserFullRequestDto dto) {
        if (dto == null) {
            return null;
        }
        User entity = new User();
        entity.setEmail(dto.getEmail());
        entity.setUsername(dto.getUsername());
        entity.setPassword(dto.getPassword());
        return entity;
    }

}
