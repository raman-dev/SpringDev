package com.hackerman.activitytracker.user;

import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Primary
public class MyUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //check username exists
        Optional<MyUser> userEntity = userRepository.findByEmail(username);
        if (!userEntity.isPresent()){
            throw UsernameNotFoundException.fromUsername(username);
        }
        MyUser user = userEntity.get();
        return  User.withDefaultPasswordEncoder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles("USER")
                .build();
    }
}
