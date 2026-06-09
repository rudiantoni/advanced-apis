package com.myapps.bavariamunich.component;

import com.myapps.bavariamunich.auth.JwtUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthDetailsComponent {

    public JwtUserDetails getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getDetails() == null) {
            throw new IllegalStateException("No authenticated user in context");
        }
        return (JwtUserDetails) auth.getDetails();
    }
}
