package com.template.api.auth;

import com.template.api.UnitTests;
import com.template.api.application.usecases.auth.RegisterCommand;
import com.template.api.application.usecases.auth.RegisterCommandHandler;
import com.template.api.domain.exceptions.UnauthorizedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class RegisterTests extends UnitTests {
    @BeforeEach
    public void setUp() {
        userRepository.clear();
        createFakeUser("1");
    }

    private RegisterCommandHandler createHandler() {
        return new RegisterCommandHandler(userRepository, passwordHasher, mailer, apiLogger);
    }

    @Nested
    class HappyPath {
        @Test
        void shouldRegisterTheUser() {
            var command = new RegisterCommand("correct@example.fr", "CorrectPassword123456789@");
            var handler = createHandler();
            var response = handler.handle(command);
            assertNotNull(response.getId());
            var user = userRepository.findById(response.getId());
            assertTrue(user.isPresent());
            assertEquals(command.getEmailAddress(), user.get().getEmailAddress());
            assertNotEquals(command.getPassword(), user.get().getPassword());
            assertNotNull(user.get().getVerificationCode());
            assertFalse(user.get().isActive());

            verify(mailer, times(1)).sendVerificationCode(command.getEmailAddress(), user.get().getVerificationCode());
        }
    }

    @Nested
    class BadPath {
        @Test
        void shouldNotRegisterTheUserIfTheEmailIsAlreadyUsed() {
            var command = new RegisterCommand("already@example.fr", "AlreadyExist123456789@");
            var handler = createHandler();
            var exception = assertThrows(UnauthorizedException.class, () -> handler.handle(command));
            assertEquals("User already exist", exception.getMessage());
        }
    }
}
