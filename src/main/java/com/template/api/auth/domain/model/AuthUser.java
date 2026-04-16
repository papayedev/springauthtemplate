package com.template.api.auth.domain.model;

public class AuthUser {
    private String id;
    private String emailAddress;

    public AuthUser() {}

    public AuthUser(String id, String emailAddress) {
        this.id = id;
        this.emailAddress = emailAddress;
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
