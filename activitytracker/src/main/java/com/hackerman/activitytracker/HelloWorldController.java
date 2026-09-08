package com.hackerman.activitytracker;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    public record Person (String firstName, String lastName){};


    @GetMapping("/index")
    public ResponseEntity<Person> index(){
        return ResponseEntity.ok().body(new Person("Raman","Deep"));
    }


}
