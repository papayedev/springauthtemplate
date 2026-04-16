package com.template.api.auth.domain.viewmodel;

public class LoggedInUserViewModel {
    private String id;
    private String emailAddress;
    private String accessToken;
    private String refreshToken;

    public LoggedInUserViewModel() {}

    public LoggedInUserViewModel(String id, String emailAddress, String accessToken, String refreshToken) {
        this.id = id;
        this.emailAddress = emailAddress;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public String getId() {
        return id;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
