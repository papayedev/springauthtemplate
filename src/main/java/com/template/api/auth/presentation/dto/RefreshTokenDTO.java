package com.template.api.auth.presentation.dto;

public class RefreshTokenDTO {
    private String refreshToken;

    public RefreshTokenDTO() {}
    public RefreshTokenDTO(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
