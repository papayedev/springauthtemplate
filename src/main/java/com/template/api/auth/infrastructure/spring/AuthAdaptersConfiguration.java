package com.template.api.auth.infrastructure.spring;

import com.template.api.auth.application.ports.AuthContext;
import com.template.api.core.application.ports.Mailer;
import com.template.api.auth.application.ports.UserRepository;
import com.template.api.auth.infrastructure.persistence.jpa.SQLUserAccessor;
import com.template.api.auth.infrastructure.persistence.jpa.SQLUserRepository;
import com.template.api.core.application.services.TemplateService;
import com.template.api.core.infrastructure.spring.SpringMailer;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
public class AuthAdaptersConfiguration {
    @Bean
    public UserRepository userRepository(SQLUserAccessor accessor) {
        return new SQLUserRepository(accessor);
    }

    @Bean
    public AuthContext getAuthContext() {
        return new SpringAuthContext();
    }

    @Bean
    public Mailer getMailer(JavaMailSender javaMailSender, TemplateService templateService) {
        return new SpringMailer(javaMailSender, templateService);
    }
}
