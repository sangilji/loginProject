package com.example.login.config;

import com.example.login.domain.CustomRoleHierarchyImpl;
import com.example.login.domain.RoleHierarchy;
import com.example.login.service.RoleHierarchyService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class SecurityInitializer implements ApplicationRunner {

    private final RoleHierarchyService roleHierarchyService;
    private final RoleHierarchyImpl roleHierarchy;
    @Override
    public void run(ApplicationArguments args) throws Exception {
//        Map<String, String> allHierarchyToMap = roleHierarchyService.findAllHierarchyToMap();
//        roleHierarchy.setHierarchy(allHierarchyToMap);
        String allHierarchy = roleHierarchyService.findAllHierarchy();
        roleHierarchy.setHierarchy(allHierarchy);

    }
}
