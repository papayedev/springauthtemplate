package com.template.api.auth;

import com.template.api.UnitTests;
import com.template.api.auth.application.usecases.LoginCommand;
import com.template.api.auth.application.usecases.LoginCommandHandler;
import com.template.api.auth.domain.exceptions.ForbiddenException;
import com.template.api.auth.domain.exceptions.NotFoundException;
import com.template.api.auth.domain.exceptions.UnauthorizedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoginTests extends UnitTests {

    @BeforeEach
    public void setUp() {
        userRepository.clear();
        createFakeUser();
    }

    private LoginCommandHandler createHandler() {
        return new LoginCommandHandler(userRepository, jwtService, passwordHasher, apiLogger);
    }

    @Nested
    class HappyPath {
        @Test
        public void shouldLogin() {
            activateUser();
            var command = new LoginCommand("already@example.fr", "Azerty123456789@");
            var handler = createHandler();
            var response = handler.handle(command);
            assertEquals("1", response.getId());
            assertEquals("already@example.fr", response.getEmailAddress());
            assertNotNull(response.getAccessToken());
            assertNotNull(response.getRefreshToken());
        }
    }

    @Nested
    class BadPath {
        @Test
        public void shouldNotLoginIfUserDoesNotExist() {
            var command = new LoginCommand("contact@example.fr", "Azerty123456789@");
            var handler = createHandler();
            var exception = assertThrows(NotFoundException.class, () -> handler.handle(command));
            assertEquals("User was not found", exception.getMessage());
        }

        @Test
        public void shouldNotLoginIfPasswordIsWrong() {
            var command = new LoginCommand("already@example.fr", "Azerty1@");
            var handler = createHandler();
            var exception = assertThrows(UnauthorizedException.class, () -> handler.handle(command));
            assertEquals("You are not authorized to perform this action", exception.getMessage());
        }

        @Test
        public void shouldNotLoginIfUserIsInactive() {
            var command = new LoginCommand("already@example.fr", "Azerty123456789@");
            var handler = createHandler();
            var exception = assertThrows(ForbiddenException.class, () -> handler.handle(command));
            assertEquals("User is not active", exception.getMessage());
        }
    }
}
