package com.example.login.config;

import com.example.login.domain.CustomRoleHierarchyImpl;
import com.example.login.service.SecurityResourceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleHierarchyVoter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록 됨.
@Slf4j
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig {

    private final SecurityResourceService securityResourceService;

    @Bean
    public BCryptPasswordEncoder encodedPwd() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationManager authenticationManager = authenticationManager(http.getSharedObject(AuthenticationConfiguration.class));

        CustomUsernamePasswordAuthenticationFilter authenticationFilter = getAuthenticationFilter(authenticationManager);
        FilterSecurityInterceptor filterSecurityInterceptor = customFilterSecurityInterceptor();
        filterSecurityInterceptor.setAuthenticationManager(authenticationManager);

        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(filterSecurityInterceptor, FilterSecurityInterceptor.class);

        http.csrf().disable();
        http.authorizeRequests()
//                .antMatchers("/user/**").authenticated()
//                .antMatchers("/manger/**").access("hasRole('ADMIN') or hasRole('MANAGER')")
//                .antMatchers("/admin/**").access("hasRole('ADMIN')")
                .anyRequest().authenticated()
                .and()
                .formLogin().disable()
                .logout().logoutUrl("/logout")
                .logoutSuccessUrl("/");
//                .loginPage("/loginForm")
//                .usernameParameter("loginId")
//                .loginProcessingUrl("/login")// login 주소가 호출이 되면 시큐리티가 로그인 진행해줌
//                .failureHandler(customFailureHandler)
//                .defaultSuccessUrl("/")
//                .and()
//                .oauth2Login()
//                .loginPage("/loginForm") // 구글 로그인이 완료된 후처리 필요(엑세스 토큰 + 사용자 프로필 정보O)
//                .userInfoEndpoint()
//                .userService(principalOauth2UserService);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public CustomUsernamePasswordAuthenticationFilter getAuthenticationFilter(AuthenticationManager authenticationManager) {
        CustomUsernamePasswordAuthenticationFilter authFilter = new CustomUsernamePasswordAuthenticationFilter();
        authFilter.setFilterProcessesUrl("/login");
        authFilter.setAuthenticationManager(authenticationManager);
        authFilter.setUsernameParameter("userId");
        authFilter.setPasswordParameter("password");
        return authFilter;
    }


    @Bean
    public RoleHierarchyImpl roleHierarchy(){
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        return roleHierarchy;
    }

    @Bean
    public AccessDecisionVoter<? extends Object> roleVoter() {
        RoleHierarchyVoter roleHierarchyVoter = new RoleHierarchyVoter(roleHierarchy());
        return roleHierarchyVoter;
    }

    private List<AccessDecisionVoter<?>> getAccessDecisionVoters() {
        List<AccessDecisionVoter<? extends Object>> accessDecisionVoters = new ArrayList<>();
        accessDecisionVoters.add(roleVoter());
        return accessDecisionVoters;
    }

    private AccessDecisionManager affirmativeBased() {
        AffirmativeBased affirmativeBased = new AffirmativeBased(getAccessDecisionVoters());
        return affirmativeBased;
    }

    @Bean
    public FilterSecurityInterceptor customFilterSecurityInterceptor() throws Exception {
        FilterSecurityInterceptor filterSecurityInterceptor = new FilterSecurityInterceptor();
        filterSecurityInterceptor.setSecurityMetadataSource(urlFilterInvocationSecurityMetadataSource());
        filterSecurityInterceptor.setAccessDecisionManager(affirmativeBased());
        return filterSecurityInterceptor;
    }

    @Bean
    public UrlFilterInvocationSecurityMetadataSource urlFilterInvocationSecurityMetadataSource() throws Exception{
        return new UrlFilterInvocationSecurityMetadataSource(urlResourcesMapFactoryBean().getObject(),securityResourceService);
    }

    private UrlResourcesMapFactoryBean urlResourcesMapFactoryBean() {

        UrlResourcesMapFactoryBean urlResourcesMapFactoryBean = new UrlResourcesMapFactoryBean();
        urlResourcesMapFactoryBean.setSecurityResourceService(securityResourceService);

        return urlResourcesMapFactoryBean;
    }
}
