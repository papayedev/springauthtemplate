package com.template.api.application.usecases.auth;

import an.awesome.pipelinr.Command;
import com.template.api.domain.viewmodel.IdResponse;

public class ActivateAccountCommand implements Command<IdResponse> {
    private String email;
    private String verificationCode;

    public ActivateAccountCommand() {

    }

    public ActivateAccountCommand(String email, String verificationCode) {
        this.email = email;
        this.verificationCode = verificationCode;
    }

    public String getEmail() {
        return email;
    }

    public String getVerificationCode() {
        return verificationCode;
    }
}
