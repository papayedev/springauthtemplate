package com.template.api.auth.infrastructure.services;

import com.template.api.auth.application.services.PasswordHasher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BcryptPasswordHasher implements PasswordHasher {
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public String hash(String password) {
        return encoder.encode(password);
    }

    @Override
    public boolean match(String clearPassword, String hashedPassword) {
        return encoder.matches(clearPassword, hashedPassword);
    }
}