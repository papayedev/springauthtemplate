package com.template.api.infrastructure.spring;

import com.template.api.application.usecases.auth.*;
import com.template.api.core.application.ports.Mailer;
import com.template.api.application.ports.UserRepository;
import com.template.api.application.services.JwtService;
import com.template.api.application.services.PasswordHasher;
import com.template.api.core.application.ports.APILogger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthUseCasesConfiguration {
    @Bean
    public RegisterCommandHandler registerCommandHandler(
            UserRepository userRepository,
            PasswordHasher passwordHasher,
            Mailer mailer,
            APILogger apiLogger
    ) {
        return new RegisterCommandHandler(userRepository, passwordHasher, mailer, apiLogger);
    }

    @Bean
    public LoginCommandHandler loginCommandHandler(
            UserRepository userRepository,
            JwtService jwtService,
            PasswordHasher passwordHasher,
            APILogger apiLogger
    ) {
        return new LoginCommandHandler(userRepository, jwtService, passwordHasher, apiLogger);
    }

    @Bean
    public ActivateAccountCommandHandler activeAccountCommandHandler(
            UserRepository userRepository,
            APILogger apiLogger
    ) {
        return new ActivateAccountCommandHandler(userRepository, apiLogger);
    }

    @Bean
    public RefreshTokenCommandHandler refreshTokenCommandHandler(
            UserRepository userRepository,
            JwtService jwtService,
            APILogger apiLogger
    ) {
        return new RefreshTokenCommandHandler(jwtService, userRepository, apiLogger);
    }

    @Bean
    public ResetPasswordRequestCommandHandler resetPasswordRequestCommandHandler(
            UserRepository userRepository,
            Mailer mailer,
            APILogger apiLogger
    ) {
        return new ResetPasswordRequestCommandHandler(userRepository, mailer, apiLogger);
    }

    @Bean
    public UpdatePasswordCommandHandler updatePasswordCommandHandler(
            UserRepository userRepository,
            PasswordHasher passwordHasher,
            APILogger apiLogger
    ) {
        return new UpdatePasswordCommandHandler(userRepository, passwordHasher, apiLogger);
    }
}
