package com.template.api.auth.application.usecases;

import an.awesome.pipelinr.Command;
import com.template.api.auth.domain.viewmodel.VoidResponse;

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
