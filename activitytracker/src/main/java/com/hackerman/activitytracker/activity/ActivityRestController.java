package com.hackerman.activitytracker.activity;

import com.hackerman.activitytracker.activity.repository.ActivityOutputDTO;
import com.hackerman.activitytracker.activity.repository.ActivityInputDTO;
import com.hackerman.activitytracker.activity.repository.ActivityRepository;
import com.hackerman.activitytracker.user.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;
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

    @GetMapping("/get/activity/names/self")
    public List<String> getMyActivityNames(@CurrentUser MyUserDetails user){
        //guaranteed to exist since cannot create user without db entity
//        MyUser user = userRepository.findByEmail(username).get();
        return activityRepository.findAllUniqueNamesForOwner(user.getId());
    }

    @GetMapping("/get/activity/all")
    public List<ActivityOutputDTO> getAllActivity(@CurrentUser MyUserDetails user){
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        //guaranteed to exist since cannot create user without db entity
//        MyUser user = userRepository.findByEmail(username).get();
        return activityRepository.findByOwnerId(user.getId());
    }

    @GetMapping("/get/activity/{id}")
    public Optional<ActivityOutputDTO> getActivity(@CurrentUser MyUserDetails user, @PathVariable Long id){
//        Long userId = userRepository.findByEmail(username).get().getId();
        return activityRepository.findByEntityId(user.getId(),id);
    }

    @PostMapping("/create/activity")
    public ResponseEntity createActivityDtoWithDatetime(@Valid @RequestBody ActivityInputDTO activityInputDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            bindingResult.getAllErrors().forEach(x -> System.out.println(x));
            return ResponseEntity.badRequest().build();
        }

        Activity activity = new Activity(activityInputDTO.getName(),
                activityInputDTO.getStartTimeIso8601(),
                activityInputDTO.getEndTimeIso8601(),null);
        var savedActivity = activityRepository.save(activity);
        Long id = savedActivity.getEntityId();

        URI location = ServletUriComponentsBuilder
                .fromPath("/activity/{id}")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.created(location).body(activityInputDTO);
    }

    @GetMapping
    public ResponseEntity userActivityDetails(@CurrentUser MyUserDetails myUserDetails){
        MyUser user = userRepository.findById(myUserDetails.getId()+"").get();
        UserOutputDTO userOutputDTO = new UserOutputDTO(user);
        List<ActivityOutputDTO> activityList = activityRepository.findByOwnerId(myUserDetails.getId());

        return ResponseEntity.ok().body(Map.of("activities ",activityList,"user",userOutputDTO));
    }
}
