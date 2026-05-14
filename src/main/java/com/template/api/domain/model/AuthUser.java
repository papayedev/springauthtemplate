package com.template.api.domain.model;

import com.template.api.domain.valueobject.Role;

public class AuthUser {
    private String id;
    private String emailAddress;
    private String role;

    public AuthUser() {}

    public AuthUser(String id, String emailAddress, String role) {
        this.id = id;
        this.emailAddress = emailAddress;
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
