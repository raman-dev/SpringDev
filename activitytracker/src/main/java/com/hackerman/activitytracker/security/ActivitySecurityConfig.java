package com.hackerman.activitytracker.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.Nullable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;

import java.io.IOException;

@Configuration
@EnableWebSecurity
public class ActivitySecurityConfig {

    @Bean
    @Order(1)
    public SecurityFilterChain activityCrudFilterChain(HttpSecurity http,SecurityContextRepository securityContextRepository){
        //enable this chain for only the following path
        http.securityMatchers((requestMatcherConfigurer -> {
            requestMatcherConfigurer.requestMatchers("/logout");
            requestMatcherConfigurer.requestMatchers("/api/**");
            requestMatcherConfigurer.requestMatchers("/get/**");
            requestMatcherConfigurer.requestMatchers("/user/**");
            requestMatcherConfigurer.requestMatchers("/create/**");
        }));

        http.securityContext((securityContext) -> {
            securityContext.securityContextRepository(securityContextRepository);
        });

        http.csrf((csrf) -> csrf.disable())
            .authorizeHttpRequests(authz -> {
                authz
                        //anyone can read all activity names
                        .requestMatchers(HttpMethod.GET,"/get/activity/names").permitAll()
                        //user based
                        .requestMatchers(HttpMethod.GET,"/get/**").authenticated()
                        .requestMatchers(HttpMethod.POST,"/create").hasRole("USER")
                        .requestMatchers(HttpMethod.GET,"/user/details").hasRole("USER")


                        .requestMatchers(HttpMethod.POST,"/api/login").permitAll()
                        .requestMatchers(HttpMethod.POST,"/logout").permitAll()
                        .anyRequest().authenticated();
            });
        http.logout((logout) -> {
            logout
                    .logoutUrl("/logout")
                    .addLogoutHandler((request, response, authentication) -> {
                        System.out.println("***** LOGOUT HANDLER EXECUTED *****");
//                        response.setHeader("Test-Header","Eh yo buddy");
                        response.setHeader("Clear-Site-Data","\"*\"");
                    })
//                    .addLogoutHandler(new HeaderWriterLogoutHandler(
//                            new ClearSiteDataHeaderWriter(
//                                    ClearSiteDataHeaderWriter.Directive.ALL
//                            ))
//                    )
                    .logoutSuccessHandler((request, response, authentication) -> {
                        System.out.println("--------LOGOUT SUCCESS HANDLER RAN----------");
                    });
        });
        http.httpBasic(Customizer.withDefaults());//http basic sends user and password with every request
        http.formLogin(Customizer.withDefaults());//session based security, user pass once on success return session id use that every request
        return http.build();
    }


    @Bean
    @Order(2)
    public SecurityFilterChain userSecurityFilterChain(HttpSecurity http){
        //enable this security chain for the following paths
        http.securityMatchers(requestMatcherConfigurer -> {
            requestMatcherConfigurer.requestMatchers("/home");
            requestMatcherConfigurer.requestMatchers("/signup");
            requestMatcherConfigurer.requestMatchers("/login/**");
        });
        http.csrf((csrf) -> csrf.disable())
                .authorizeHttpRequests(authz -> {
                    authz
                            .requestMatchers(HttpMethod.POST,"/signup").permitAll()
                            .requestMatchers(HttpMethod.GET,"/home").authenticated();
                });
        http.formLogin((form) -> {
            form
                    .defaultSuccessUrl("/home",true)
                    .permitAll();
        });
        return http.build();
    }

    @Bean
    PasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(authenticationProvider);
    }

    @Bean
    public SecurityContextRepository securityContextRepository(){
        return new HttpSessionSecurityContextRepository();
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
