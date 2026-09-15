package com.hackerman.activitytracker.activity.repository;

public class ActivityRepoContainer {
    private ActivityRepository repository;

    public ActivityRepoContainer(ActivityRepository repository) {
        this.repository = repository;
    }

    public ActivityRepository getRepository() {
        return repository;
    }
}
