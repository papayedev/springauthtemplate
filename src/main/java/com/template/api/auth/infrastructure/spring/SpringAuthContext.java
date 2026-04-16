package com.template.api.auth.infrastructure.spring;

import com.template.api.auth.application.ports.AuthContext;
import com.template.api.auth.domain.model.AuthUser;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class SpringAuthContext implements AuthContext {
    @Override
    public boolean isAuthenticated() {
        return SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
    }

    @Override
    public Optional<AuthUser> getAuthUser() {
        return Optional.ofNullable(
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
        ).map(auth -> {
            if (auth.getPrincipal() instanceof AuthUser) {
                return (AuthUser) auth.getPrincipal();
            }

            return null;
        });
    }
}

