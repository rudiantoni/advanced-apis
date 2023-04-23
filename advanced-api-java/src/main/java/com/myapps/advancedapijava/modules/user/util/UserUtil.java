package com.myapps.advancedapijava.modules.user.util;


import com.myapps.advancedapijava.modules.user.dto.UserDto;
import com.myapps.advancedapijava.modules.user.dto.UserRespDto;
import com.myapps.advancedapijava.modules.user.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserUtil {
  private UserUtil() {
  }

  // Convert: To DTOs
  public static UserDto toDto(User entity) {
    return UserDto.builder()
      .id(entity.getId())
      .email(entity.getEmail())
      .username(entity.getUsername())
      .password(entity.getPassword())
      .build();
  }

  public static UserRespDto toRespDto(User entity) {
    return UserRespDto.builder()
      .id(entity.getId())
      .email(entity.getEmail())
      .username(entity.getUsername())
      .build();
  }

  public static List<UserDto> toDtoList(List<User> entityList) {
    List<UserDto> dtoList = new ArrayList<>();
    entityList.forEach(it -> {
      UserDto dto = toDto(it);
      dtoList.add(dto);
    });
    return dtoList;
  }

  // Convert: To Entities
  public static User toEntityBase(UserDto dto, Boolean ignoreIds) {
    User entity = new User();
    if (!ignoreIds) {
      entity.setId(dto.getId());
    }
    entity.setEmail(dto.getEmail());
    entity.setUsername(dto.getUsername());
    entity.setPassword(dto.getPassword());
    return entity;
  }

  static public User toEntity(UserDto dto) {
    return toEntityBase(dto, false);
  }

  static public User toEntityNoId(UserDto dto) {
    return toEntityBase(dto, true);
  }

  static private List<User> toEntityListBase(List<UserDto> dtoList, Boolean ignoreIds) {
    List<User> entityList = new ArrayList<>();
    dtoList.forEach(it -> {
      User entity = toEntityBase(it, ignoreIds);
      entityList.add(entity);
    });
    return entityList;
  }

  static public List<User> toEntityList(List<UserDto> dtoList) {
    return toEntityListBase(dtoList, false);
  }

  static public List<User> toEntityListNoId(List<UserDto> dtoList) {
    return toEntityListBase(dtoList, true);
  }

  // Convert: Entities Update
  static public User updateEntityBase(
    User oldEntity, User newEntity, Boolean ignorePassword, Boolean ignoreIds, Boolean ignoreNulls
  ) {
    User resEntity = new User();

    if (!ignoreIds) {
      resEntity.setId(newEntity.getId());
    } else {
      resEntity.setId(oldEntity.getId());
    }
    if (!ignorePassword) {
      resEntity.setPassword(newEntity.getPassword());
    } else {
      resEntity.setPassword(Optional.ofNullable(newEntity.getPassword()).orElse(oldEntity.getPassword()));
    }
    if (!ignoreNulls) {
      resEntity.setEmail(newEntity.getEmail());
      resEntity.setUsername(newEntity.getUsername());
    } else {
      resEntity.setEmail(Optional.ofNullable(newEntity.getEmail()).orElse(oldEntity.getEmail()));
      resEntity.setUsername(Optional.ofNullable(newEntity.getUsername()).orElse(oldEntity.getUsername()));
    }

    return resEntity;
  }

  static public User updateEntityNoIdNoPassword(User oldEntity, User newEntity) {
    return updateEntityBase(oldEntity, newEntity, true, true, false);
  }

  static public User updateEntityNoIdNoPasswordNotNull(User oldEntity, User newEntity) {
    return updateEntityBase(oldEntity, newEntity, true, true, true);
  }

  static public User updateEntityFull(User oldEntity, User newEntity) {
    return updateEntityBase(oldEntity, newEntity, false, false,false);
  }

}