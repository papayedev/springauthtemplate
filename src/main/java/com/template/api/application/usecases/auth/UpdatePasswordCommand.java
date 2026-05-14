package com.template.api.application.usecases.auth;

import an.awesome.pipelinr.Command;
import com.template.api.domain.viewmodel.VoidResponse;

public class UpdatePasswordCommand implements Command<VoidResponse> {
    private String email;
    private String verificationCode;
    private String password;

    public UpdatePasswordCommand() {}

    public UpdatePasswordCommand(String email, String verificationCode, String password) {
        this.email = email;
        this.verificationCode = verificationCode;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public String getPassword() {
        return password;
    }
}
