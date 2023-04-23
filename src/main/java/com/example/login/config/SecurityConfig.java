package com.example.login.config;

import com.example.login.auth.PrincipalDetails;
import com.example.login.domain.Member;
import com.example.login.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록 됨.
@Slf4j
@RequiredArgsConstructor
public class SecurityConfig{
    private final CustomFailureHandler customFailureHandler;
    private final MemberRepository memberRepository;

    @Bean
    public BCryptPasswordEncoder encodedPwd(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/user/**").authenticated()
                .antMatchers("/manger/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/loginForm")
                .usernameParameter("loginId")
                .loginProcessingUrl("/login")// loginProc 주소가 호출이 되면 시큐리티가 로그인 진행해줌
                .failureHandler(customFailureHandler)
                .defaultSuccessUrl("/");

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
