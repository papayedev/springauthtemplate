package com.template.api.auth.application.usecases;

import an.awesome.pipelinr.Command;
import com.template.api.auth.domain.viewmodel.AccessTokenViewModel;

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
