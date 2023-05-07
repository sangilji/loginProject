package com.example.login.config;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.*;

public class UrlFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private Map<RequestMatcher, List<ConfigAttribute>> requestMap;

    public UrlFilterInvocationSecurityMetadataSource() {
        this.requestMap = new HashMap<>();

//        requestMap.put(new AntPathRequestMatcher("/admin"),
//                Collections.singletonList(new SecurityConfig("ROLE_ADMIN")));
//
//        //ROLE_ADMIN, ROLE_COMPANY -> `/company` GET 허용
//        requestMap.put(new AntPathRequestMatcher("/company", "GET"),
//                Arrays.asList(new SecurityConfig("ROLE_ADMIN"), new SecurityConfig("ROLE_COMPANY")));
//        //ROLE_ADMIN -> '/company' POST 허용
//        requestMap.put(new AntPathRequestMatcher("/company", "POST"),
//                Collections.singletonList(new SecurityConfig("ROLE_ADMIN")));
//
//        //ROLE_ADMIN, ROLE_COMPANY, ROLE_USER -> `/user` GET 허용
//        requestMap.put(new AntPathRequestMatcher("/user", "GET"),
//                Arrays.asList(new SecurityConfig("ROLE_ADMIN"), new SecurityConfig("ROLE_COMPANY"),
//                        new SecurityConfig("ROLE_USER")));
//        //ROLE_ADMIN, ROLE_COMPANY -> '/user' POST 허용
//        requestMap.put(new AntPathRequestMatcher("/user", "POST"),
//                Arrays.asList(new SecurityConfig("ROLE_ADMIN"), new SecurityConfig("ROLE_COMPANY")));


    }
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        Set<ConfigAttribute> allAttributes = new HashSet<>();

        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
