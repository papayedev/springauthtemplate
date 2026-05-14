package com.template.api.application.usecases.auth;

import an.awesome.pipelinr.Command;
import com.template.api.domain.viewmodel.AccessTokenViewModel;

public class RefreshTokenCommand implements Command<AccessTokenViewModel> {
    private String refreshToken;

    public RefreshTokenCommand() {}

    public RefreshTokenCommand(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
