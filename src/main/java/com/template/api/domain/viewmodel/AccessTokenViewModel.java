package com.template.api.domain.viewmodel;

public class AccessTokenViewModel {
    private String accessToken;

    public AccessTokenViewModel() {}

    public AccessTokenViewModel(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
