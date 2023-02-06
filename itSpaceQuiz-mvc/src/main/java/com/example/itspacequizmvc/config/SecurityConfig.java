package com.example.itspacequizmvc.config;

import com.example.itspacequizcommon.entity.UserType;
import com.example.itspacequizmvc.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsImpl userDetails;
    private final PasswordEncoder passwordEncoder;

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable();
        http
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/successLogin")
                .usernameParameter("email")
                .passwordParameter("password")
                .and().logout()
                .logoutSuccessUrl("/")
                .and();
        http.authorizeHttpRequests(requests -> requests
                .requestMatchers("/").permitAll()
                .requestMatchers("/login").permitAll()
                .requestMatchers("/h2-console").permitAll()
                .requestMatchers("/user/register").permitAll()
                .requestMatchers("/successLogin").hasAnyAuthority(UserType.TEACHER.name(), UserType.STUDENT.name(
                ))
                .requestMatchers("/user").hasAnyAuthority((UserType.STUDENT.name()))
                .requestMatchers("/deleteQuiz/{id}").hasAnyAuthority((UserType.TEACHER.name()))
                .requestMatchers(HttpMethod.GET, "/quizCreate").hasAnyAuthority((UserType.TEACHER.name()))
                .requestMatchers("/editQuiz/{id}").hasAnyAuthority((UserType.TEACHER.name()))
                .requestMatchers(HttpMethod.POST, "/quiz").hasAnyAuthority((UserType.TEACHER.name()))
                .requestMatchers("/deleteQuestion/{id}").hasAnyAuthority((UserType.TEACHER.name()))
                .requestMatchers(HttpMethod.GET, "/editQuestion/{id}").hasAnyAuthority((UserType.TEACHER.name()))
                .requestMatchers(HttpMethod.POST, "/editQuestion").hasAnyAuthority((UserType.TEACHER.name()))
                .requestMatchers(HttpMethod.POST, "/question").hasAnyAuthority((UserType.TEACHER.name()))
                .requestMatchers(HttpMethod.GET, "/questions/byQuiz/{id}").hasAnyAuthority((UserType.TEACHER.name()))
                .requestMatchers(HttpMethod.GET, "/editQuestion").hasAnyAuthority((UserType.TEACHER.name()))
                .requestMatchers(HttpMethod.GET, "/editQuestion/{id}").hasAnyAuthority((UserType.TEACHER.name()))
                .anyRequest().authenticated());


        return http.build();
    }

    @Bean
    protected AuthenticationManager auth(HttpSecurity httpSecurity) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
//        authenticationManagerBuilder.eraseCredentials(false);
        authenticationManagerBuilder.userDetailsService(userDetails)
                .passwordEncoder(passwordEncoder);
        return authenticationManagerBuilder.build();
    }


}
