package com.template.api.application.services;

import com.template.api.domain.model.AuthUser;
import com.template.api.domain.model.User;

public interface JwtService {
    String generateAccessToken(User user);
    AuthUser parseAccessToken(String accessToken);
    String generateRefreshToken(User user);
    AuthUser parseRefreshToken(String refreshToken);
}
