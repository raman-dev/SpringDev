package com.hackerman.activitytracker.activity;

import com.hackerman.activitytracker.activity.repository.ActivityDTO;
import com.hackerman.activitytracker.activity.repository.ActivityInputDTO;
import com.hackerman.activitytracker.activity.repository.ActivityRepository;
import com.hackerman.activitytracker.user.MyUser;
import com.hackerman.activitytracker.user.UserCreateDTO;
import com.hackerman.activitytracker.user.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriBuilderFactory;

import java.net.URI;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@RestController
public class ActivityRestController {

    //spring di's this without autowired
    ActivityRepository activityRepository;
    UserRepository userRepository;

    public ActivityRestController(ActivityRepository activityRepository, UserRepository userRepository){
        this.activityRepository = activityRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/get/activity/names")
    public List<String> getActivityNames(){
        return activityRepository.findAllUniqueNames();
    }

    @GetMapping("/get/activity/all")
    public List<ActivityDTO> getAllActivity(){
        return activityRepository.findAllBy();
    }

    @GetMapping("/get/activity/{id}")
    public Optional<Activity> getActivity(@PathVariable Long id){
        return activityRepository.findById(id);
    }

    @PostMapping("/create/activity")
    public ResponseEntity createActivityDtoWithDatetime(@Valid @RequestBody ActivityInputDTO activityInputDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            bindingResult.getAllErrors().forEach(x -> System.out.println(x));
            return ResponseEntity.badRequest().build();
        }

        Activity activity = new Activity(activityInputDTO.getName(),
                activityInputDTO.getStartTimeIso8601(),
                activityInputDTO.getEndTimeIso8601());
        var savedActivity = activityRepository.save(activity);
        Long id = savedActivity.getEntityId();

        URI location = ServletUriComponentsBuilder
                .fromPath("/activity/{id}")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.created(location).body(activityInputDTO);
    }



}
