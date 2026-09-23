package com.hackerman.activitytracker.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<MyUser, String> {
    public Optional<MyUser> findByEmail(String email);
}
