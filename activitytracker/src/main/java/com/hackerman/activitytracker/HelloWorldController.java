package com.hackerman.activitytracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloWorldController {

    public record Person (String firstName, String lastName){};

    @Autowired
    MyRepository myRepoContainer;

    @GetMapping("/get-person")
    public Person index(){
        return new Person("Raman","Deep");
    }


    @GetMapping("/get-activity")
    public Activity getActivity(){
        return new Activity("Programming","8am","11am");
    }
    @PostMapping("/create-activity")
    public Activity createActivity(@RequestBody Activity activity){
        //check if startTimeStamp in iso format
        //check if endtimestamp in iso format
        //post to db here
        return activity;
    }

    @PostMapping("/create-activty-jpa")
    public Activity createActivityJpa(@RequestBody Activity activity){
        myRepoContainer.save(activity);
        return activity;
    }

}
