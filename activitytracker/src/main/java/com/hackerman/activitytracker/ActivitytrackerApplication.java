package com.hackerman.activitytracker;

import com.hackerman.activitytracker.activity.repository.ActivityRepoContainer;
import com.hackerman.activitytracker.activity.repository.ActivityRepository;
import com.hackerman.activitytracker.security.MySecurityConfig;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;


/**
 * What is this?
 * what should this do?
 * what is an activity?
 * i want to log what i do throughout a day and show stats and summary of daily, weekly and monthly activities
 * time spent doing xyz how many times
 * example:
 * 		day:
 * 		watching youtube
 * 			1100 - 1145
 * 	    coding
 * 	    	1230 1330
 * 	    browsing internet
 * 	    	1400 1600
 * 	    exercising
 * 	    	1800 2000
 * 	   week:
 * 		 timespent
 * 		 	watching youtube:
 * 		 		15hours
 * 		 	browsing internet:
 * 		 		20 hours
 * 		    coding:
 * 		    	3 hours
 * 		 daily average:
 * 		 	...
 * 		 most frequent
 * 		 most time in day on
 *
 * 	   monthly:
 * 	   	  timespent
 * 			etc...
 */

@SpringBootApplication
@Import(MySecurityConfig.class)
public class ActivitytrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ActivitytrackerApplication.class, args);
	}

	@Bean
	public ActivityRepoContainer activityRepoContainer(ActivityRepository repository){
		return new ActivityRepoContainer(repository);
	}

	@Bean
	CommandLineRunner clr(){
		return  x -> {
			System.out.println("Hello From Raman!");
		};
	}



}
