package com.myapps.apijava.entity;


import com.myapps.apijava.dto.UserSecureDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq")
  @SequenceGenerator(name = "users_seq", sequenceName = "public.users_seq", allocationSize = 1)
  @Column(name = "id")
  private Long id;

  @Column(name = "email")
  private String email;

  @Column(name = "username")
  private String username;

  @Column(name = "password")
  private String password;

  @CreationTimestamp
  @Column(name = "created_at")
  private OffsetDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at")
  private OffsetDateTime updatedAt;

  @Column(name = "active")
  private Boolean active;

  @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinTable(
    name = "user_permission",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "permission_id")
  )
  private List<Permission> permissions;

  public UserSecureDto toSecureDto() {
    List<String> permissions = this.permissions.stream().map(Permission::getName).collect(Collectors.toList());
    return UserSecureDto.builder()
      .id(this.id)
      .username(this.username)
      .email(this.email)
      .permissions(permissions)
      .build();
  }
}
