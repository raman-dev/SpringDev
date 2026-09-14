package com.hackerman.activitytracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ActivityRestController {

    //spring di's this without autowired
    ActivityRepository activityRepository;

    public ActivityRestController(ActivityRepository activityRepository){
        this.activityRepository = activityRepository;
    }

    @GetMapping("/get/activity/names")
    public List<String> getActivityNames(){
        return activityRepository.findAllUniqueNames();
    }

    @GetMapping("/get/activity/all")
    public List<Activity> getAllActivity(){
        return (List<Activity>)(activityRepository.findAll());
    }

    @PostMapping("/create")
    public Activity createActivity(Activity activity){
        activityRepository.save(activity);
        return activity;
    }
}
