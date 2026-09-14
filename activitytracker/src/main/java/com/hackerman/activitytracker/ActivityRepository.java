package com.hackerman.activitytracker;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ActivityRepository extends CrudRepository<Activity, Long> {
    public List<Activity> findByName(String name);
}
