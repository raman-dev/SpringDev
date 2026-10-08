package com.hackerman.activitytracker.activity.repository;

import com.hackerman.activitytracker.activity.Activity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ActivityRepository extends CrudRepository<Activity, Long> {
    public List<Activity> findByName(String name);

    public List<ActivityOutputDTO> findByOwnerId(Long ownerId);

    public Optional<ActivityOutputDTO> findByEntityId(Long id);

    @Query("SELECT curr FROM Activity curr WHERE  curr.owner.id=:ownerId AND curr.id=:id")
    public Optional<ActivityOutputDTO> findByEntityId(@Param("ownerId") Long ownerId,@Param("id") Long id);

    @Query("SELECT DISTINCT curr.name FROM Activity curr WHERE curr.owner.id =:ownerId")
    public List<String> findAllUniqueNamesForOwner(@Param("ownerId") Long ownerId);

    @Query("SELECT DISTINCT curr.name from Activity curr")
    public List<String> findAllUniqueNames();

    public List<ActivityOutputDTO> findAllBy();
}
