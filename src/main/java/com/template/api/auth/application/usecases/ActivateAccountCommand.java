package com.template.api.auth.application.usecases;

import an.awesome.pipelinr.Command;
import com.template.api.auth.domain.viewmodel.IdResponse;

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
