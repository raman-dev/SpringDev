package com.hackerman.activitytracker.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<MyUser, String> {
    public MyUser findByEmail(String email);
}
