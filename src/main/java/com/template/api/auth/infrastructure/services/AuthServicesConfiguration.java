package com.template.api.auth.infrastructure.services;

import com.template.api.auth.application.services.JwtService;
import com.template.api.auth.application.services.PasswordHasher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthServicesConfiguration {
    @Value("${jwt.access.secret}")
    private String jwtAccessSecret;

    @Value("${jwt.access.expiration}")
    private String jwtAccessExpiration;

    @Value("${jwt.refresh.secret}")
    private String jwtRefreshSecret;

    @Value("${jwt.refresh.expiration}")
    private String jwtRefreshExpiration;

    @Bean
    public PasswordHasher passwordHasher() {
        return new BcryptPasswordHasher();
    }

    @Bean
    public JwtService jwtService() {
        return new ConcreteJwtService(
                jwtAccessSecret,
                Long.parseLong(jwtAccessExpiration),
                jwtRefreshSecret,
                Long.parseLong(jwtRefreshExpiration)
        );
    }
}
