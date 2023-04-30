package com.example.login.config;

import com.example.login.config.oauth.PrincipalOauth2UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록 됨.
@Slf4j
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig{
    private final CustomFailureHandler customFailureHandler;
    private final PrincipalOauth2UserService principalOauth2UserService;
    @Bean
    public BCryptPasswordEncoder encodedPwd(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/user/**").authenticated()
                .antMatchers("/manger/**").access("hasRole('ADMIN') or hasRole('MANAGER')")
                .antMatchers("/admin/**").access("hasRole('ADMIN')")
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/loginForm")
                .usernameParameter("loginId")
                .loginProcessingUrl("/login")// login 주소가 호출이 되면 시큐리티가 로그인 진행해줌
                .failureHandler(customFailureHandler)
                .defaultSuccessUrl("/")
                .and()
                .oauth2Login()
                .loginPage("/loginForm") // 구글 로그인이 완료된 후처리 필요(엑세스 토큰 + 사용자 프로필 정보O)
                .userInfoEndpoint()
                .userService(principalOauth2UserService);
        return http.build();
    }

//    @Bean
//    public UserDetailsService userDetailsService(){
//        return username -> {
//            Member member = memberRepository.findByUsername(username);
//            if (member == null) {
//                throw new UsernameNotFoundException(username);
//            }
//            return new PrincipalDetails(member);
//        };
//    }

}
