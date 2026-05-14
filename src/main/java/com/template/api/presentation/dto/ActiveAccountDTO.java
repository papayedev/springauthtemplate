package com.template.api.presentation.dto;

public class ActiveAccountDTO {

    private String email;

    private String verificationCode;

    public ActiveAccountDTO() {}

    public ActiveAccountDTO(String email, String verificationCode) {
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