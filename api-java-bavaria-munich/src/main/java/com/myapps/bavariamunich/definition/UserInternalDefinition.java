package com.myapps.bavariamunich.definition;

public class UserInternalDefinition {

    private Long id;

    private String email;

    private String username;

    private String password;

    public UserInternalDefinition() {
    }

    public UserInternalDefinition(Long id, String email, String username, String password) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "UserInternalDefinition{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='***'" +
                '}';
    }

}
