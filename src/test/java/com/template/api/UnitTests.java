package com.template.api;

import com.template.api.application.ports.UserRepository;
import com.template.api.application.services.JwtService;
import com.template.api.application.services.PasswordHasher;
import com.template.api.domain.model.User;
import com.template.api.domain.valueobject.Role;
import com.template.api.domain.viewmodel.LoggedInUserViewModel;
import com.template.api.infrastructure.services.BcryptPasswordHasher;
import com.template.api.infrastructure.services.ConcreteJwtService;
import com.template.api.core.application.ports.APILogger;
import com.template.api.core.application.ports.Mailer;
import com.template.api.core.infrastructure.spring.SpringLogger;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@SpringBootTest
@ActiveProfiles("test")
@Import(PostgresSQLTestConfiguration.class)
@AutoConfigureMockMvc
@Transactional
@ExtendWith(MockitoExtension.class)
public class UnitTests {
    @Mock
    protected Mailer mailer;

    protected JwtService jwtService = new ConcreteJwtService(
            "fake-secret-access_secretfake-secret-access_secret",
            10,
            "fake-secret-access_secretfake-secret-access_secret",
            10
    );

    @Autowired
    protected UserRepository userRepository;

    protected PasswordHasher passwordHasher = new BcryptPasswordHasher();
    protected final APILogger apiLogger = new SpringLogger();

    protected void activateUser() {
        var user = userRepository.findById("1").get();
        user.makeActive();
        userRepository.save(user);
    }

    protected void expiredVerificationCode() {
        var user = userRepository.findById("1").get();
        user.updateVerificationCodeExpirationDate(-15);
        userRepository.save(user);
    }

    protected LoggedInUserViewModel createAccessAndRefreshToken() {
        var user = userRepository.findByEmailAddress("already@example.fr").orElse(null);
        if (user == null) {
            user = new User(
                    UUID.randomUUID().toString(),
                    "already@example.fr",
                    "Azerty123456789@",
                    Role.USER
            );
            user.resetPassword(passwordHasher.hash("Azerty123456789@"));
            user.createVerificationCode(15);
            userRepository.save(user);
        }
        return new LoggedInUserViewModel(null, null, jwtService.generateAccessToken(user), jwtService.generateRefreshToken(user));
    }

    protected User createFakeUser(String id) {
        var emailId = id.equals("1") ? "" : id;
        var user = new User(
                id,
                "already" + emailId + "@example.fr",
                "Azerty123456789@",
                Role.USER
        );

        user.resetPassword(passwordHasher.hash("Azerty123456789@"));
        user.createVerificationCode(15);
        userRepository.save(user);
        return user;
    }
}
