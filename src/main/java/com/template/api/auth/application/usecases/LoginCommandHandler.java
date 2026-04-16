package com.template.api.auth.application.usecases;

import an.awesome.pipelinr.Command;
import com.template.api.auth.domain.exceptions.UnauthorizedException;
import com.template.api.auth.application.ports.UserRepository;
import com.template.api.auth.application.services.JwtService;
import com.template.api.auth.application.services.PasswordHasher;
import com.template.api.auth.domain.viewmodel.LoggedInUserViewModel;
import com.template.api.core.application.ports.APILogger;
import com.template.api.auth.domain.exceptions.ForbiddenException;
import com.template.api.auth.domain.exceptions.NotFoundException;

public class LoginCommandHandler implements Command.Handler<LoginCommand, LoggedInUserViewModel> {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordHasher passwordHasher;
    private final APILogger apiLogger;

    public LoginCommandHandler(UserRepository userRepository,
                               JwtService jwtService,
                               PasswordHasher passwordHasher,
                               APILogger apiLogger) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordHasher = passwordHasher;
        this.apiLogger = apiLogger;
    }

    @Override
    public LoggedInUserViewModel handle(LoginCommand loginCommand) {
        apiLogger.info("Login command received");
        var user = this.userRepository
                .findByEmailAddress(loginCommand.getEmailAddress())
                .orElseThrow(() -> new NotFoundException("User"));

        var match = this.passwordHasher.match(
                loginCommand.getPassword(),
                user.getPassword()
        );

        if (!match) {
            throw new UnauthorizedException();
        }

        if (!user.isActive()) {
            throw new ForbiddenException("User is not active");
        }

        var accessToken = this.jwtService.generateAccessToken(user);
        var refreshToken = this.jwtService.generateRefreshToken(user);

        apiLogger.info("Refresh and access token generated");

        return new LoggedInUserViewModel(user.getId(), user.getEmailAddress(), accessToken, refreshToken);
    }
}

