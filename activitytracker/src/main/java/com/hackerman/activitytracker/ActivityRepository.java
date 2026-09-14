package com.hackerman.activitytracker;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ActivityRepository extends CrudRepository<Activity, Long> {
    public List<Activity> findByName(String name);

    @Query("SELECT DISTINCT curr.name from Activity curr")
    public List<String> findAllUniqueNames();

    public List<ActivityDTO> findAllBy();
}
