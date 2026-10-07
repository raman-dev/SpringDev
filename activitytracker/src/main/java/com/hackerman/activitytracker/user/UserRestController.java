package com.hackerman.activitytracker.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class UserRestController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityContextRepository securityContextRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
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

        MyUser newUser = new MyUser(userCreateDTO.getEmail(),
                passwordEncoder.encode(userCreateDTO.getPassword()));
        userRepository.save(newUser);
        return ResponseEntity.ok().body(Map.of("message","User created with email: "+newUser.getEmail()));
    }

    public record LoginData(
            @NotBlank(message = "Username cannot be empty")
            String username,
            @NotBlank(message="Password cannot be empty")
            String password){};

    @PostMapping("/api/login")
    public ResponseEntity loginFunction(
            @Valid @RequestBody LoginData loginData,
            BindingResult bindingResult,
            HttpServletRequest request, HttpServletResponse response){

        if (bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body(
                    bindingResult.getAllErrors()
                            .stream()
                            .map(ObjectError::getDefaultMessage)
                            .toList());
        }

        Authentication authenticationRequest = UsernamePasswordAuthenticationToken
                .unauthenticated(
                        loginData.username(),
                        loginData.password());


        Authentication authenticationResponse = authenticationManager
                .authenticate(authenticationRequest);

        if (authenticationResponse.isAuthenticated()){
            //must populate securitycontextrepository manually
            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
            securityContext.setAuthentication(authenticationResponse);
            //static for thread
            SecurityContextHolder.setContext(securityContext);
            securityContextRepository.saveContext(securityContext,request,response);

            String username = authenticationResponse.getName();
            System.out.println("authenticated.user => "+username);
            return ResponseEntity.ok().body(Map.of("username",username));
        }

        return ResponseEntity.badRequest().body(List.of("Unknown error"));
    }

    class UserOutputDTO {
        String username;
        String email;

        public UserOutputDTO(String username, String email) {
            this.username = username;
            this.email = email;
        }

        public UserOutputDTO() {
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        @Override
        public String toString() {
            return "UserOutputDTO{" +
                    "username='" + username + '\'' +
                    ", email='" + email + '\'' +
                    '}';
        }
    }
}
