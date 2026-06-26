package com.myapps.bavariamunich.entity;

import javax.persistence.*;

@Entity
@Table(schema = "public", name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq")
    @SequenceGenerator(name = "users_seq", sequenceName = "public.users_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "email", nullable = false, unique = true, length = 128)
    private String email;

    @Column(name = "username", nullable = false, length = 128)
    private String username;

    @Column(name = "password", nullable = false, length = 512)
    private String password;

    public User() {
    }

    public User(Long id, String email, String username, String password) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
