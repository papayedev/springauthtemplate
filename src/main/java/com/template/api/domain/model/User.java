package com.template.api.domain.model;

import com.template.api.domain.valueobject.EmailAddress;
import com.template.api.domain.valueobject.Password;
import com.template.api.domain.valueobject.Role;
import com.template.api.domain.valueobject.VerificationCode;

import java.time.LocalDateTime;

public class User {
    private String id;
    private EmailAddress emailAddress;
    private Password password;
    private VerificationCode verificationCode;
    private LocalDateTime verificationCodeExpirationDate;
    private boolean active;
    private Role role;

    private User() {}

    public User(String id, String emailAddress, String passwordValue, Role role) {
        this.id           = id;
        this.emailAddress = new EmailAddress(emailAddress);
        if (passwordValue != null) {
            this.password = new Password(passwordValue, null);
        }
        this.active       = false;
        this.role = role;
    }

    public static User fromPersistence(String id, String emailAddress, String passwordHash,
                                       String verificationCode, LocalDateTime verificationCodeExpirationDate,
                                       boolean active, Role role) {
        final User user = new User();
        user.id = id;
        user.emailAddress = new EmailAddress(emailAddress);
        user.password = new Password(null, passwordHash);
        if (verificationCode != null) {
            user.verificationCode = new VerificationCode(verificationCode);
        }
        user.verificationCodeExpirationDate = verificationCodeExpirationDate;
        user.active = active;
        user.role = role;
        return user;
    }

    public boolean isActive() {
        return active;
    }

    public void makeActive() {
        this.active = true;
    }

    public void makeInactive() {
        this.active = false;
    }

    public void createVerificationCode(int minutesExpiration) {
        this.verificationCode = new VerificationCode();
        this.updateVerificationCodeExpirationDate(minutesExpiration);
    }

    public void updateVerificationCodeExpirationDate(int minutes) {
        this.verificationCodeExpirationDate = LocalDateTime.now().plusMinutes(minutes);
    }

    public void resetVerificationCode() {
        this.verificationCode = null;
    }

    public void clearVerificationCode() {
        this.verificationCode               = null;
        this.verificationCodeExpirationDate = null;
    }

    public void resetPassword(String newHashedPassword) {
        this.password = new Password(null, newHashedPassword);
        clearVerificationCode();
    }

    public boolean isVerificationCodeExpired() {
        return verificationCodeExpirationDate == null
                || LocalDateTime.now().isAfter(verificationCodeExpirationDate);
    }

    public boolean isUser() {
        return role == Role.USER;
    }

    public boolean isAdmin() {
        return role == Role.ADMIN;
    }

    public String getEmailAddress() {
        return emailAddress.getValue();
    }

    public LocalDateTime getVerificationCodeExpirationDate() {
        return verificationCodeExpirationDate;
    }

    public String getVerificationCode() {
        return verificationCode == null ? null : verificationCode.getValue();
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password.getHashedPassword();
    }

}