package com.hackerman.activitytracker.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserRestController {
    
    private UserRepository userRepository;

//    @Autowired
    private AuthenticationManager authenticationManager;

    public UserRestController(UserRepository userRepository,AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/signup/create")
    public ResponseEntity createUser(@Valid @RequestBody UserCreateDTO userCreateDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            bindingResult.getAllErrors().forEach((e) -> {System.out.println(e);});
            return ResponseEntity.badRequest().build();
        }

        //create user object with repository
        Optional<MyUser> alreadyExists = userRepository.findByEmail(userCreateDTO.getEmail());
        if (alreadyExists.isPresent()){
            return ResponseEntity.badRequest().body(List.of(new String[]{"User with this email already exists"}));
        }

        if (!userCreateDTO.getPassword().equals(userCreateDTO.getMatchingPassword())){
            return ResponseEntity.badRequest().body(List.of(new String[]{"Passwords do not match."}));
        }

        PasswordEncoder passwordEncoder = bCryptPasswordEncoder();
        MyUser newUser = new MyUser(userCreateDTO.getEmail(),
                passwordEncoder.encode(userCreateDTO.getPassword()));
        userRepository.save(newUser);
        return ResponseEntity.ok().body(List.of(new String[]{"User created with email: "+newUser.getEmail()}));
    }

    public record LoginRequest(@NotBlank  String username,@NotBlank String password){};

    @PostMapping("/login")
    public ResponseEntity<Void> loginFunction(@Valid @RequestBody LoginRequest loginRequest,BindingResult bindingResult){

        Authentication authenticationRequest = UsernamePasswordAuthenticationToken.unauthenticated(
                loginRequest.username(),
                loginRequest.password());

        Authentication authenticationResponse = authenticationManager.authenticate(authenticationRequest);
        if (authenticationResponse.isAuthenticated()){
            //return what redirect or logged in or populate securitycontextrepository?

            return ResponseEntity.accepted().build();
        }

        return ResponseEntity.badRequest().build();
    }


    @Bean
    PasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder(10);
    }

}
