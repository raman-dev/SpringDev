package com.hackerman.activitytracker.activity;

import com.hackerman.activitytracker.activity.repository.ActivityDTO;
import com.hackerman.activitytracker.activity.repository.ActivityInputDTO;
import com.hackerman.activitytracker.activity.repository.ActivityRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public List<ActivityDTO> getAllActivity(){
        return activityRepository.findAllBy();
    }

    @GetMapping("/get/activity/{id}")
    public Optional<Activity> getActivity(@PathVariable Long id){
        return activityRepository.findById(id);
    }

    @PostMapping("/create")
    public Activity createActivity(@RequestBody Activity activity){
        activityRepository.save(activity);
        return activity;
    }

//    @PostMapping("/create-dto")
//    public ResponseEntity createActivityDto(@Valid @RequestBody ActivityInputDTO activityInputDTO, BindingResult bindingResult){
//        if (bindingResult.hasErrors()){
//            bindingResult.getAllErrors().forEach(x -> System.out.println(x));
//            return ResponseEntity.badRequest().build();
//        }
//        Activity activity = new Activity(activityInputDTO.getName(),activityInputDTO.getStartTimeStamp(),activityInputDTO.getEndTimeStamp());
//        return ResponseEntity.ok().body(activity);
//    }

    @PostMapping("/create-dto-datetime")
    public ResponseEntity createActivityDtoWithDatetime(@Valid @RequestBody ActivityInputDTO activityInputDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            bindingResult.getAllErrors().forEach(x -> System.out.println(x));
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(activityInputDTO);
    }
}
