package com.template.api.presentation.dto;

public class UpdatePasswordDTO {
    private String email;

    private String verificationCode;

    private String password;

    public UpdatePasswordDTO() {}

    public UpdatePasswordDTO(String email, String verificationCode, String password) {
        this.email = email;
        this.verificationCode = verificationCode;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
