package com.template.api.auth.application.usecases;

import an.awesome.pipelinr.Command;
import com.template.api.auth.domain.viewmodel.IdResponse;

public class RegisterCommand implements Command<IdResponse> {
    private String emailAddress;
    private String password;

    public RegisterCommand() {

    }

    public RegisterCommand(String emailAddress, String password) {
        this.emailAddress = emailAddress;
        this.password = password;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPassword() {
        return password;
    }
}
