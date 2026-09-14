package com.hackerman.activitytracker;

public class MyRepository {

    public ActivityRepository repository;

    public MyRepository(ActivityRepository repository){
        this.repository = repository;
    }

    public void save(Activity activity){
        repository.save(activity);
    }
}
