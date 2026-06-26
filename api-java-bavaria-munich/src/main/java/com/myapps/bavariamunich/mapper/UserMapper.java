package com.myapps.bavariamunich.mapper;

import com.myapps.bavariamunich.definition.DefaultUserDefinition;
import com.myapps.bavariamunich.definition.UserInternalDefinition;
import com.myapps.bavariamunich.dto.UserSecureDto;
import com.myapps.bavariamunich.dto.UserWriteDto;
import com.myapps.bavariamunich.entity.User;

public class UserMapper {

    public static UserSecureDto toSecureDto(User entity) {
        if (entity == null) {
            return null;
        }
        UserSecureDto dto = new UserSecureDto();
        dto.setId(entity.getId());
        dto.setEmail(entity.getEmail());
        dto.setUsername(entity.getUsername());
        return dto;
    }

    public static UserInternalDefinition toInternalDefinition(DefaultUserDefinition entity) {
        if (entity == null) {
            return null;
        }
        return new UserInternalDefinition(
                entity.getId(), entity.getEmail(), entity.getUsername(), entity.getPassword()
        );
    }

    public static UserInternalDefinition toInternalDefinition(User entity) {
        if (entity == null) {
            return null;
        }
        return new UserInternalDefinition(
                entity.getId(), entity.getEmail(), entity.getUsername(), entity.getPassword()
        );
    }

    public static User toEntity(UserWriteDto dto) {
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
