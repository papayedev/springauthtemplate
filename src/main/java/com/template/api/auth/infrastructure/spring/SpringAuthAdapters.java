package com.template.api.auth.infrastructure.spring;

import com.template.api.core.application.ports.APILogger;
import com.template.api.core.application.services.TemplateService;
import com.template.api.core.infrastructure.services.TemplateServiceImpl;
import com.template.api.core.infrastructure.spring.SpringLogger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringAuthAdapters {
    @Bean
    APILogger apiLogger() {
        return new SpringLogger();
    }

    @Bean
    TemplateService templateService(freemarker.template.Configuration freemarker) {
        return new TemplateServiceImpl(freemarker);
    }
}
