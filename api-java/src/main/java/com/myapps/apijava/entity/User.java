package com.myapps.apijava.entity;


import com.myapps.apijava.dto.UserSecureDto;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
  private Long id;
  private String email;
  private String username;
  private String password;

  public UserSecureDto toSecureDto(){
    return UserSecureDto.builder()
      .id(this.id)
      .username(this.username)
      .email(this.email)
      .build();
  }
}
