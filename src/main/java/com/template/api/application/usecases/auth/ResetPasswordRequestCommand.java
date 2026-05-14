package com.template.api.application.usecases.auth;

import an.awesome.pipelinr.Command;
import com.template.api.domain.viewmodel.VoidResponse;

public class ResetPasswordRequestCommand implements Command<VoidResponse> {
    private String email;

    public ResetPasswordRequestCommand() {}

    public ResetPasswordRequestCommand(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
