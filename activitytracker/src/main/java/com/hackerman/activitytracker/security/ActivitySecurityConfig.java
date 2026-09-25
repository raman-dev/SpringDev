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
        //enable this chain for only the following path
        http.securityMatchers((requestMatcherConfigurer -> {
            requestMatcherConfigurer.requestMatchers("/get/**");
            requestMatcherConfigurer.requestMatchers("/create/**");
        }));
        http.csrf((csrf) -> csrf.disable())
            .authorizeHttpRequests(authz -> {
                authz
                        .requestMatchers(HttpMethod.GET,"/get/*/*").permitAll()
                        .requestMatchers(HttpMethod.GET,"/get/*").permitAll()
                        .requestMatchers(HttpMethod.POST,"/create").hasRole("USER")
                        .anyRequest().authenticated();
            });
        http.httpBasic(Customizer.withDefaults());//http basic sends user and password with every request
        http.formLogin(Customizer.withDefaults());//session based security, user pass once on success return session id use that every request
        return http.build();
    }


    @Bean
    public SecurityFilterChain userSecurityFilterChain(HttpSecurity http){
        //enable this security chain for the following paths
        http.securityMatchers(requestMatcherConfigurer -> {
            requestMatcherConfigurer.requestMatchers("/signup/**");
            requestMatcherConfigurer.requestMatchers("/login/**");
        });
        http.csrf((csrf) -> csrf.disable())
                .authorizeHttpRequests(authz -> {
                    authz.requestMatchers(HttpMethod.POST,"/signup/create").permitAll()
                         .requestMatchers(HttpMethod.POST,"/login").permitAll();
                });
        http.formLogin(Customizer.withDefaults());
        return http.build();
    }


    //create a default user on boot
//    @Bean
//    UserDetailsService userDetailsService(){
//        UserDetails userDetails = User
//                .withDefaultPasswordEncoder()
//                .username("user")
//                .password("password")
//                .roles("USER")
//                .build();
//        return new InMemoryUserDetailsManager(userDetails);
//    }

}
