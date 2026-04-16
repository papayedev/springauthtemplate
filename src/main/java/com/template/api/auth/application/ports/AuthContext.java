package com.template.api.auth.application.ports;

import com.template.api.auth.domain.model.AuthUser;

import java.util.Optional;

public interface AuthContext {
    boolean isAuthenticated();
    Optional<AuthUser> getAuthUser();
}
