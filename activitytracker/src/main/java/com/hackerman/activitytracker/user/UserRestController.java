package com.hackerman.activitytracker.user;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
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
        MyUser newUser = new MyUser(userCreateDTO.getEmail(),userCreateDTO.getPassword());
        userRepository.save(newUser);
        return ResponseEntity.created(null).build();
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequest loginRequest){
        
    }

    public record LoginRequest(String username,String password){};



}
