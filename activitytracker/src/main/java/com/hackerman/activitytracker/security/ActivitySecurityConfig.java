package com.hackerman.activitytracker.security;

import org.springframework.boot.web.server.Http2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class ActivitySecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http){
        http.csrf((csrf) -> csrf.disable());
        http.authorizeHttpRequests((authz) -> {
            authz
                    .requestMatchers("/get/*").permitAll()
                    .requestMatchers("/create").hasRole("USER")
                    .requestMatchers("/create/dto").hasRole("USER");
        });
        return http.build();
    }

}
