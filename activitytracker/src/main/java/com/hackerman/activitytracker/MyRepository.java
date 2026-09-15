package com.hackerman.activitytracker;

import com.hackerman.activitytracker.activity.Activity;
import com.hackerman.activitytracker.activity.repository.ActivityRepository;

public class MyRepository {

    public ActivityRepository repository;

    public MyRepository(ActivityRepository repository){
        this.repository = repository;
    }

    public void save(Activity activity){
        repository.save(activity);
    }
}
