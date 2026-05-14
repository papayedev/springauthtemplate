package com.template.api.application.ports;

import com.template.api.domain.model.AuthUser;

import java.util.Optional;

public interface AuthContext {
    boolean isAuthenticated();
    Optional<AuthUser> getAuthUser();
}
