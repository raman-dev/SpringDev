package com.hackerman.activitytracker.user;

import jakarta.validation.Valid;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserRestController {
    
    private UserRepository userRepository;

    public UserRestController(UserRepository userRepository) {
        this.userRepository = userRepository;
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

    @Bean
    PasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder(10);
    }

}
