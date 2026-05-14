package com.template.api.auth;

import com.template.api.UnitTests;
import com.template.api.application.usecases.auth.RefreshTokenCommand;
import com.template.api.application.usecases.auth.RefreshTokenCommandHandler;
import com.template.api.domain.exceptions.UnauthorizedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RefreshTokenTests extends UnitTests {
    @BeforeEach
    public void setUp() {
        userRepository.clear();
        createFakeUser("1");
        activateUser();
    }

    private RefreshTokenCommandHandler createHandler() {
        return new RefreshTokenCommandHandler(jwtService, userRepository, apiLogger);
    }

    @Nested
    class HappyPath {
        @Test
        void shouldRefreshToken() {
            var command = new RefreshTokenCommand(createAccessAndRefreshToken().getRefreshToken());
            var handler = createHandler();
            var response = handler.handle(command);
            assertNotNull(response);
            assertNotNull(response.getAccessToken());
        }
    }

    @Nested
    class BadPath {
        @Test
        void shouldNotRefreshIfTokenIsBad() {
            var command = new RefreshTokenCommand("");
            var handler = createHandler();
            var exception = assertThrows(UnauthorizedException.class, () -> handler.handle(command));
            assertEquals("You are not authorized to perform this action", exception.getMessage());
        }
    }
}
