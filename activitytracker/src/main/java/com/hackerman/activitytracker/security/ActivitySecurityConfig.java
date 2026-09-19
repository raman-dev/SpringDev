package com.hackerman.activitytracker.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class ActivitySecurityConfig {

    @Bean
    public SecurityFilterChain activityCrudFilterChain(HttpSecurity http){
        http.csrf((csrf) -> csrf.disable())
            .authorizeHttpRequests(authz -> {
            authz
                    .requestMatchers(HttpMethod.GET,"/get/*/*").permitAll()
                    .requestMatchers(HttpMethod.GET,"/get/*").permitAll()
                    .requestMatchers("/create").hasRole("USER")
                    .anyRequest().authenticated();
//                    .requestMatchers("/create/dto").hasRole("USER");
        });
        http.httpBasic(Customizer.withDefaults());
        return http.build();
    }



}
