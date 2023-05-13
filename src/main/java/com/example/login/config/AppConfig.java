package com.example.login.config;

import com.example.login.repository.ResourceRepository;
import com.example.login.service.SecurityResourceService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public SecurityResourceService securityResourceService(ResourceRepository resourceRepository) {
        SecurityResourceService securityResourceService = new SecurityResourceService(resourceRepository);
        return securityResourceService;
    }
}
