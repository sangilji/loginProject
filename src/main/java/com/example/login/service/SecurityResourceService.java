package com.example.login.service;

import com.example.login.domain.Resources;
import com.example.login.repository.ResourceRepository;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class SecurityResourceService {

    public SecurityResourceService(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    private ResourceRepository resourceRepository;

    public void setResourceRepository(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    public LinkedHashMap<RequestMatcher, List<ConfigAttribute>> getResourceList(){

        LinkedHashMap<RequestMatcher, List<ConfigAttribute>> result = new LinkedHashMap<>();
        List<Resources> resourcesList = resourceRepository.findAllResources();
        resourcesList.forEach(re ->{
            List<ConfigAttribute> configAttributeList = new ArrayList<>();
            re.getRoleSet().forEach(role->{
               configAttributeList.add(new SecurityConfig(role.getRoleName()));
               result.put(new AntPathRequestMatcher(re.getResourceName()),configAttributeList);
            });
        });

        return result;
    }
}
