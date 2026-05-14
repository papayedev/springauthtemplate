package com.template.api;

import com.template.api.application.ports.UserRepository;
import com.template.api.application.services.JwtService;
import com.template.api.application.services.PasswordHasher;
import com.template.api.domain.model.User;
import com.template.api.domain.valueobject.Role;
import com.template.api.domain.viewmodel.LoggedInUserViewModel;
import com.template.api.infrastructure.services.BcryptPasswordHasher;
import com.template.api.core.application.ports.Mailer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.UUID;

@SpringBootTest
@ActiveProfiles("test")
@Import(PostgresSQLTestConfiguration.class)
@AutoConfigureMockMvc
@Transactional
public class IntegrationTests {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected JwtService jwtService;

    @MockitoBean
    protected Mailer mailer;

    protected PasswordHasher passwordHasher = new BcryptPasswordHasher();

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

}