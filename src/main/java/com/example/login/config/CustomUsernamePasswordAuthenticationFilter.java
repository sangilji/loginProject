package com.example.login.config;

import com.example.login.domain.LoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.MimeTypeUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.stream.Collectors;

public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authenticationToken;
        if (request.getContentType().equals(MimeTypeUtils.APPLICATION_JSON_VALUE)) {
            // json
            try {
                LoginRequest loginDto = objectMapper.readValue(
                        request.getReader().lines().collect(Collectors.joining()), LoginRequest.class);
                authenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getUserId(), loginDto.getPassword());
            } catch (Exception e) {
                throw new AuthenticationServiceException("Request Content-Type(application/json) Parsing Error");
            }
        } else {
            // form-data
            String username = obtainUsername(request);
            String password = obtainPassword(request);
            authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        }
        this.setDetails(request, authenticationToken);
        return this.getAuthenticationManager().authenticate(authenticationToken);
    }

}
