package com.template.api.application.usecases.auth;

import an.awesome.pipelinr.Command;
import com.template.api.domain.viewmodel.LoggedInUserViewModel;

public record LoginCommand(
        String emailAddress,
        String password
) implements Command<LoggedInUserViewModel> {
}

