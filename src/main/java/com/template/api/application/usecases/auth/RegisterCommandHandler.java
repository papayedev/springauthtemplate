package com.template.api.application.usecases.auth;

import an.awesome.pipelinr.Command;
import com.template.api.domain.exceptions.UnauthorizedException;
import com.template.api.core.application.ports.Mailer;
import com.template.api.application.ports.UserRepository;
import com.template.api.application.services.PasswordHasher;
import com.template.api.domain.model.User;
import com.template.api.domain.valueobject.Role;
import com.template.api.domain.viewmodel.IdResponse;
import com.template.api.core.application.ports.APILogger;

import java.util.UUID;

public class RegisterCommandHandler implements Command.Handler<RegisterCommand, IdResponse> {
    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;
    private final Mailer mailer;
    private final APILogger apiLogger;

    public RegisterCommandHandler(UserRepository userRepository,
                                  PasswordHasher passwordHasher,
                                  Mailer mailer,
                                  APILogger apiLogger) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
        this.mailer = mailer;
        this.apiLogger = apiLogger;
    }

    private User generateUser(String email, String password) {
        var user = new User(
                UUID.randomUUID().toString(),
                email,
                password,
                Role.USER
        );
        user.resetPassword(passwordHasher.hash(password));
        user.createVerificationCode(15);
        userRepository.save(user);
        return user;
    }

    private void sendVerificationCode(User user) {
        var verificationCode = user.getVerificationCode();
        mailer.sendVerificationCode(user.getEmailAddress(),  verificationCode);
    }

    @Override
    public IdResponse handle(RegisterCommand registerCommand) {
        apiLogger.info("Register command received");
        var maybeUser = userRepository.findByEmailAddress(registerCommand.getEmailAddress());
        if (maybeUser.isPresent()) {
            throw new UnauthorizedException("User already exist");
        }
        var user = generateUser(registerCommand.getEmailAddress(), registerCommand.getPassword());
        sendVerificationCode(user);
        apiLogger.info("New verification code generated");
        return new IdResponse(user.getId());
    }
}