package com.template.api.auth.model;

import com.template.api.domain.model.User;
import com.template.api.domain.valueobject.Role;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserTests {

    private static final String VALID_EMAIL = "test@example.fr";
    private static final String VALID_PASSWORD = "ValidPassword123@";
    private static final String HASHED_PASSWORD = "$2a$10$hashedpassword";

    @Nested
    class Construction {
        @Test
        void shouldCreateUserWithValidData() {
            var id = UUID.randomUUID().toString();

            var user = new User(id, VALID_EMAIL, VALID_PASSWORD, Role.USER);

            assertEquals(id, user.getId());
            assertEquals(VALID_EMAIL, user.getEmailAddress());
            assertTrue(user.isUser());
            assertFalse(user.isActive());
            assertNull(user.getVerificationCode());
            assertNull(user.getVerificationCodeExpirationDate());
        }

        @Test
        void shouldThrowExceptionForInvalidEmail() {
            var id = UUID.randomUUID().toString();

            assertThrows(IllegalArgumentException.class, () ->
                    new User(id, "invalid-email", VALID_PASSWORD, Role.USER)
            );
        }

        @Test
        void shouldThrowExceptionForInvalidPassword() {
            var id = UUID.randomUUID().toString();

            assertThrows(IllegalArgumentException.class, () ->
                    new User(id, VALID_EMAIL, "weak", Role.USER)
            );
        }
    }

    @Nested
    class FromPersistence {
        @Test
        void shouldReconstructUserFromPersistence() {
            var id = UUID.randomUUID().toString();
            var verificationCode = "12345";
            var expirationDate = LocalDateTime.now().plusMinutes(15);

            var user = User.fromPersistence(
                    id,
                    VALID_EMAIL,
                    HASHED_PASSWORD,
                    verificationCode,
                    expirationDate,
                    true,
                    Role.USER
            );

            assertEquals(id, user.getId());
            assertEquals(VALID_EMAIL, user.getEmailAddress());
            assertEquals(HASHED_PASSWORD, user.getPassword());
            assertEquals(verificationCode, user.getVerificationCode());
            assertEquals(expirationDate, user.getVerificationCodeExpirationDate());
            assertTrue(user.isActive());
        }

        @Test
        void shouldReconstructUserWithoutVerificationCode() {
            var id = UUID.randomUUID().toString();

            var user = User.fromPersistence(
                    id,
                    VALID_EMAIL,
                    HASHED_PASSWORD,
                    null,
                    null,
                    false,
                    Role.USER
            );

            assertNull(user.getVerificationCode());
            assertNull(user.getVerificationCodeExpirationDate());
            assertFalse(user.isActive());
        }
    }

    @Nested
    class ActiveStatus {
        @Test
        void shouldBeInactiveByDefault() {
            var user = new User(UUID.randomUUID().toString(), VALID_EMAIL, VALID_PASSWORD, Role.USER);

            assertFalse(user.isActive());
        }

        @Test
        void shouldMakeUserActive() {
            var user = new User(UUID.randomUUID().toString(), VALID_EMAIL, VALID_PASSWORD, Role.USER);

            user.makeActive();

            assertTrue(user.isActive());
        }

        @Test
        void shouldMakeUserInactive() {
            var user = new User(UUID.randomUUID().toString(), VALID_EMAIL, VALID_PASSWORD, Role.USER);
            user.makeActive();

            user.makeInactive();

            assertFalse(user.isActive());
        }
    }

    @Nested
    class VerificationCode {
        @Test
        void shouldCreateVerificationCodeWithExpiration() {
            var user = new User(UUID.randomUUID().toString(), VALID_EMAIL, VALID_PASSWORD, Role.USER);

            user.createVerificationCode(15);

            assertNotNull(user.getVerificationCode());
            assertNotNull(user.getVerificationCodeExpirationDate());
            assertTrue(user.getVerificationCodeExpirationDate().isAfter(LocalDateTime.now()));
            assertTrue(user.getVerificationCodeExpirationDate().isBefore(LocalDateTime.now().plusMinutes(16)));
        }

        @Test
        void shouldResetVerificationCode() {
            var user = new User(UUID.randomUUID().toString(), VALID_EMAIL, VALID_PASSWORD, Role.USER);
            user.createVerificationCode(15);

            user.resetVerificationCode();

            assertNull(user.getVerificationCode());
            assertNotNull(user.getVerificationCodeExpirationDate()); // Date non effacée
        }

        @Test
        void shouldClearVerificationCodeAndExpiration() {
            var user = new User(UUID.randomUUID().toString(), VALID_EMAIL, VALID_PASSWORD, Role.USER);
            user.createVerificationCode(15);

            user.clearVerificationCode();

            assertNull(user.getVerificationCode());
            assertNull(user.getVerificationCodeExpirationDate());
        }

        @Test
        void shouldUpdateVerificationCodeExpirationDate() {
            var user = new User(UUID.randomUUID().toString(), VALID_EMAIL, VALID_PASSWORD, Role.USER);
            var beforeUpdate = LocalDateTime.now();

            user.updateVerificationCodeExpirationDate(30);

            assertNotNull(user.getVerificationCodeExpirationDate());
            assertTrue(user.getVerificationCodeExpirationDate().isAfter(beforeUpdate.plusMinutes(29)));
            assertTrue(user.getVerificationCodeExpirationDate().isBefore(beforeUpdate.plusMinutes(31)));
        }
    }

    @Nested
    class VerificationCodeExpiration {
        @Test
        void shouldBeExpiredWhenExpirationDateIsNull() {
            var user = new User(UUID.randomUUID().toString(), VALID_EMAIL, VALID_PASSWORD, Role.USER);

            assertTrue(user.isVerificationCodeExpired());
        }

        @Test
        void shouldBeExpiredWhenDateIsInThePast() {
            var user = User.fromPersistence(
                    UUID.randomUUID().toString(),
                    VALID_EMAIL,
                    HASHED_PASSWORD,
                    "12345",
                    LocalDateTime.now().minusMinutes(1),
                    false,
                    Role.USER
            );

            assertTrue(user.isVerificationCodeExpired());
        }

        @Test
        void shouldNotBeExpiredWhenDateIsInTheFuture() {
            var user = User.fromPersistence(
                    UUID.randomUUID().toString(),
                    VALID_EMAIL,
                    HASHED_PASSWORD,
                    "12345",
                    LocalDateTime.now().plusMinutes(10),
                    false,
                    Role.USER
            );

            assertFalse(user.isVerificationCodeExpired());
        }
    }

    @Nested
    class PasswordManagement {
        @Test
        void shouldResetPasswordAndClearVerificationCode() {
            var user = new User(UUID.randomUUID().toString(), VALID_EMAIL, VALID_PASSWORD, Role.USER);
            user.createVerificationCode(15);
            var newHashedPassword = "$2a$10$newhash";

            user.resetPassword(newHashedPassword);

            assertEquals(newHashedPassword, user.getPassword());
            assertNull(user.getVerificationCode());
            assertNull(user.getVerificationCodeExpirationDate());
        }
    }
}