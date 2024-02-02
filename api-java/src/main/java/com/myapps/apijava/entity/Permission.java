package com.myapps.apijava.entity;

import com.myapps.apijava.enums.PermissionType;
import jakarta.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "permission")
public class Permission {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "permission_seq")
  @SequenceGenerator(name = "permission_seq", sequenceName = "public.permission_seq", allocationSize = 1)
  @Column(name = "id")
  private Long id;
  @Column(name = "name")
  private String name;
  @Column(name = "description")
  private String description;
  @Column(name = "type")
  @Enumerated(EnumType.STRING)
  private PermissionType type;
}
