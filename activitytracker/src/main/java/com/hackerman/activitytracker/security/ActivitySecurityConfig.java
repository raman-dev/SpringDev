package com.hackerman.activitytracker.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

@Configuration
@EnableWebSecurity
public class ActivitySecurityConfig {

    @Bean
    public SecurityFilterChain activityCrudFilterChain(HttpSecurity http,SecurityContextRepository securityContextRepository){
        //enable this chain for only the following path
        http.securityMatchers((requestMatcherConfigurer -> {
            requestMatcherConfigurer.requestMatchers("/api/**");
            requestMatcherConfigurer.requestMatchers("/get/**");
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
                        .requestMatchers(HttpMethod.POST,"/api/login").permitAll()
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
            requestMatcherConfigurer.requestMatchers("/home");
            requestMatcherConfigurer.requestMatchers("/signup/**");
            requestMatcherConfigurer.requestMatchers("/login/**");
        });
        http.csrf((csrf) -> csrf.disable())
                .authorizeHttpRequests(authz -> {
                    authz
                            .requestMatchers(HttpMethod.POST,"/signup/create").permitAll()
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
