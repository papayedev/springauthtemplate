package com.template.api.auth.presentation.dto;

public class RegisterDTO {
    private String email;

    private String password;

    public RegisterDTO() {}

    public RegisterDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}