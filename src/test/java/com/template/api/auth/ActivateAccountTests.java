package com.template.api.auth;

import com.template.api.UnitTests;
import com.template.api.auth.application.usecases.ActivateAccountCommand;
import com.template.api.auth.application.usecases.ActivateAccountCommandHandler;
import com.template.api.auth.domain.exceptions.BadRequestException;
import com.template.api.auth.domain.exceptions.ForbiddenException;
import com.template.api.auth.domain.exceptions.NotFoundException;
import com.template.api.auth.domain.exceptions.UnauthorizedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ActivateAccountTests extends UnitTests {
    private String code;

    @BeforeEach
    public void setUp() {
        userRepository.clear();
        var user = createFakeUser();
        code = user.getVerificationCode();
    }

    private ActivateAccountCommandHandler createHandler() {
        return new ActivateAccountCommandHandler(userRepository, apiLogger);
    }

    @Nested
    class HappyPath {
        @Test
        public void shouldActivateTheAccount() {
            var command = new ActivateAccountCommand("already@example.fr", code);
            var handler = createHandler();
            var result = handler.handle(command);
            assertNotNull(result);
            assertEquals("1", result.getId());
            var user = userRepository.findById(result.getId()).get();
            assertTrue(user.isActive());
        }
    }

    @Nested
    class BadPath {
        @Test
        public void shouldNotActivateTheAccountIfTheUserDoesNotExist() {
            var command = new ActivateAccountCommand("error@example.fr", code);
            var handler = createHandler();
            var exception = assertThrows(NotFoundException.class, () -> handler.handle(command));
            assertEquals("User was not found", exception.getMessage());
        }

        @Test
        public void shouldNotActivateTheAccountIfTheUserIsAlreadyActivated() {
            var command = new ActivateAccountCommand("already@example.fr", code);
            activateUser();
            var handler = createHandler();
            var exception = assertThrows(ForbiddenException.class, () -> handler.handle(command));
            assertEquals("User is already active", exception.getMessage());
        }

        @Test
        public void shouldNotActivateTheAccountIfTheCodeIsBad() {
            var command = new ActivateAccountCommand("already@example.fr", "123");
            var handler = createHandler();
            var exception = assertThrows(BadRequestException.class, () -> handler.handle(command));
            assertEquals("Verification code is wrong", exception.getMessage());
        }

        @Test
        public void shouldNotActivateTheAccountIfTheCodeIsExpired() {
            expiredVerificationCode();
            var command = new ActivateAccountCommand("already@example.fr", code);
            var handler = createHandler();
            var exception = assertThrows(UnauthorizedException.class, () -> handler.handle(command));
            assertEquals("You are not authorized to perform this action", exception.getMessage());
        }
    }
}
