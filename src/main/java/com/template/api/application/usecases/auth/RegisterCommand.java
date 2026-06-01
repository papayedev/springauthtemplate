package com.template.api.application.usecases.auth;

import an.awesome.pipelinr.Command;
import com.template.api.domain.viewmodel.IdResponse;

public record RegisterCommand(
        String emailAddress,
        String password
) implements Command<IdResponse> {

}
