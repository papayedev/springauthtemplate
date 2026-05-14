package com.template.api.application.services;

public interface PasswordHasher {
    String hash(String password);
    boolean match(String clearPassword, String hashedPassword);
}
