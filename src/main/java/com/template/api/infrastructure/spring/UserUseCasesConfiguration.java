package com.template.api.infrastructure.spring;

import com.template.api.application.usecases.user.GetMyRoleCommandHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserUseCasesConfiguration {
    @Bean
    public GetMyRoleCommandHandler getMyRoleCommandHandler() {
        return new GetMyRoleCommandHandler();
    }
}
