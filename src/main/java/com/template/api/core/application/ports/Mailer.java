package com.template.api.core.application.ports;

public interface Mailer {
    void sendVerificationCode(String to, String code);
}