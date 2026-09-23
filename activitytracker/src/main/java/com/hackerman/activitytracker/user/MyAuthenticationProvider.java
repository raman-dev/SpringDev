package com.hackerman.activitytracker.user;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class MyAuthenticationProvider implements AuthenticationProvider {

    private UserRepository userRepository;

    public MyAuthenticationProvider(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public @Nullable Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        String username = authentication.getName();
//        String password = authentication.geCredentials().toString();
//
//        Optional<MyUser> savedUser = userRepository.findByEmail(username);
//        if (!savedUser.isPresent()){
//            throw new BadCredentialsException("User with username does not exist");
//        }
//
//        MyUser user = savedUser.get();
//        if (!user.getEmail().equals(username) || !user.getPassword().equals(password)){
//            throw new BadCredentialsException("Username or password does not match");
//        }
//
//        //authenticated
//        return new UsernamePasswordAuthenticationToken(username,password, AuthorityUtils.createAuthorityList("USER"));
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
