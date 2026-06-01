package com.template.api.application.usecases.auth;

import an.awesome.pipelinr.Command;
import com.template.api.domain.viewmodel.IdResponse;

public record ActivateAccountCommand(
        String email,
        String verificationCode
) implements Command<IdResponse> {


}
