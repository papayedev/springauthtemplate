package com.template.api.auth;

import com.template.api.UnitTests;
import com.template.api.auth.application.usecases.ResetPasswordRequestCommand;
import com.template.api.auth.application.usecases.ResetPasswordRequestCommandHandler;
import com.template.api.auth.domain.exceptions.ForbiddenException;
import com.template.api.auth.domain.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ResetPasswordRequestTests extends UnitTests {
    @BeforeEach
    public void setUp() {
        userRepository.clear();
        createFakeUser();
    }

    private ResetPasswordRequestCommandHandler createHandler() {
        return new ResetPasswordRequestCommandHandler(userRepository, mailer, apiLogger);
    }

    @Nested
    class HappyPath {
        @Test
        public void shouldSendRequestPasswordReset() {
            activateUser();
            var command = new ResetPasswordRequestCommand("already@example.fr");
            var handler = createHandler();
            handler.handle(command);
            var user = userRepository.findById("1").get();

            assertNotNull(user.getVerificationCode());
            assertNotNull(user.getVerificationCodeExpirationDate());

            assertFalse(user.getVerificationCodeExpirationDate().isBefore(LocalDateTime.now()));

            verify(mailer, times(1)).sendVerificationCode("already@example.fr", user.getVerificationCode());
        }
    }

    @Nested
    class BadPath {
        @Test
        public void shouldNotSendRequestPasswordResetIfUserDoesNotExist() {
            var command = new ResetPasswordRequestCommand("error@example.fr");
            var handler = createHandler();
            var exception = assertThrows(NotFoundException.class, () -> handler.handle(command));
            assertEquals("User was not found", exception.getMessage());
        }

        @Test
        public void shouldNotSendRequestPasswordResetIfUserIsAlreadyVerified() {
            var command = new ResetPasswordRequestCommand("already@example.fr");
            var handler = createHandler();
            var exception = assertThrows(ForbiddenException.class, () -> handler.handle(command));
            assertEquals("User is not active", exception.getMessage());
        }
    }
}