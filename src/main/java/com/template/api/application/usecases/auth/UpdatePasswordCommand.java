package com.template.api.application.usecases.auth;

import an.awesome.pipelinr.Command;
import com.template.api.domain.viewmodel.VoidResponse;

public record UpdatePasswordCommand(
        String email,
        String verificationCode,
        String password
) implements Command<VoidResponse> {

}
