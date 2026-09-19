package com.hackerman.activitytracker.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

//@Configuration
//@EnableWebSecurity
public class MySecurityConfig {

//    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http){

        http
                .csrf((csrf) -> csrf.disable())
                .authorizeHttpRequests(authorize -> {
                    authorize
                            .requestMatchers(HttpMethod.GET,"/get/*/*").permitAll()
                            .requestMatchers(HttpMethod.POST,"/create").permitAll()
                            .requestMatchers(HttpMethod.POST,"/create-*").permitAll()
                            .requestMatchers(HttpMethod.POST,"/create-*-*").permitAll()

                            .anyRequest().authenticated();
                });
        return http.build();
    }

}
