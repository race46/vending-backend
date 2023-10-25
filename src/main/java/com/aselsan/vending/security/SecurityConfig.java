package com.aselsan.vending.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public RequestFilter getRequestFilter(){
        return new RequestFilter();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.cors(AbstractHttpConfigurer::disable);
        http.csrf(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(req -> {
            req.requestMatchers("/api/machine/**", "/api/admin/login", "/api/img/**", "/swagger-ui.html", "swagger-ui/**", "/v3/api-docs/**").permitAll();
            req.anyRequest().authenticated();
        });

        http.addFilterBefore(getRequestFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
