package com.example.itspacequizrest.config;


import com.example.itspacequizcommon.entity.UserType;
import com.example.itspacequizrest.security.CurrentUserDetailServiceImpl;
import com.example.itspacequizrest.security.JWTAuthenticationTokenFilter;
import com.example.itspacequizrest.security.JwtAuthenticationEntryPoint;
import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CurrentUserDetailServiceImpl userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
                .and();
        http.authorizeHttpRequests(requests -> requests
                .requestMatchers("/user/auth").permitAll()
                .requestMatchers(HttpMethod.POST,"/user").permitAll()
                .requestMatchers(HttpMethod.DELETE,"/quiz/{id}").hasAnyAuthority((UserType.TEACHER.name()))
                .requestMatchers(HttpMethod.POST,"/quiz").hasAnyAuthority((UserType.TEACHER.name()))
                .requestMatchers(HttpMethod.PUT,"/quiz/{id}").hasAnyAuthority((UserType.TEACHER.name()))
                .requestMatchers(HttpMethod.PUT,"/question/{id}").hasAnyAuthority((UserType.TEACHER.name()))
                .requestMatchers(HttpMethod.POST,"/question").hasAnyAuthority((UserType.TEACHER.name()))
                .requestMatchers(HttpMethod.DELETE,"/question/{id}").hasAnyAuthority((UserType.TEACHER.name()))
                .anyRequest().authenticated())
                        .httpBasic();


        http.addFilterBefore(authenticationTokenFilterBean(),
                UsernamePasswordAuthenticationFilter.class);

        http.headers().cacheControl();
        return http.build();

    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public Filter authenticationTokenFilterBean() throws Exception {
        return new JWTAuthenticationTokenFilter();
    }

}