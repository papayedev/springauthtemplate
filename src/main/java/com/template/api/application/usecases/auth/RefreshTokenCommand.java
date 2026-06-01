package com.template.api.application.usecases.auth;

import an.awesome.pipelinr.Command;
import com.template.api.domain.viewmodel.AccessTokenViewModel;

public record RefreshTokenCommand(
        String refreshToken
) implements Command<AccessTokenViewModel> {

}
