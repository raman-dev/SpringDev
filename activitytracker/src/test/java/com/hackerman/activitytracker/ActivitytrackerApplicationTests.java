package com.hackerman.activitytracker;

import com.hackerman.activitytracker.activity.Activity;
import com.hackerman.activitytracker.activity.repository.ActivityInputDTO;
import com.hackerman.activitytracker.activity.repository.ActivityRepoContainer;
import com.hackerman.activitytracker.activity.repository.ActivityRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.swing.*;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(classes = {ActivitytrackerApplication.class},
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestRestTemplate
class ActivitytrackerApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private ActivityRepoContainer activityRepoContainer;

	static final String name = "Programming";
	static final String startTimeStamp = "8am";
	static final String endTimeStamp = "11am";


	@Test
	@Disabled
	public void testPostActivity(){

		String url = "/create-activity";
		Activity testActivity = new Activity(name,startTimeStamp,endTimeStamp);
		ResponseEntity<Activity> responseEntity = restTemplate
				.postForEntity(url,testActivity,Activity.class);

		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		Activity activity = responseEntity.getBody();

		assertActivityFields(activity,name,startTimeStamp,endTimeStamp);
	}

	@Test
	public void sampleTest(){
		System.out.println("Sample Test test");
		assertEquals(1,1);
	}

	@Test
	public void testActivityCreation(){
		Activity activity = new Activity(name,startTimeStamp,endTimeStamp);
		System.out.println("Created:\n\t" + activity);

		assertActivityFields(activity,name,startTimeStamp,endTimeStamp);
	}

	@Test
	public void testActivityDatabaseCreationWithRepository(){
		Activity testActivity = new Activity(name,startTimeStamp,endTimeStamp);
		ActivityRepository activityRepository = activityRepoContainer.getRepository();
		activityRepository.save(testActivity);

		ArrayList<Activity> list = (ArrayList<Activity>)activityRepository.findByName(testActivity.getName());

		assertThat(list.size()).isGreaterThan(0);

		Activity savedActivity = list.get(0);

		assertActivityFieldsEqual(testActivity,savedActivity);
	}

	@Test
	@Disabled
	public void testActivityDbCreationWithPostRestMapping(){
		String url="/create";
		Activity testActivity = new Activity(name,startTimeStamp,endTimeStamp);
		ResponseEntity<Activity> responseEntity = restTemplate
				.postForEntity(url,testActivity,Activity.class);

		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		//check database
		Activity dbActivity = activityRepoContainer.getRepository().findByName(testActivity.getName()).getFirst();

		assertActivityFieldsEqual(testActivity,dbActivity);
	}

	@Test
	public void testGetUniqueActivityNamesFromDB(){
		ActivityRepository repository = activityRepoContainer.getRepository();

		ArrayList<String> names = (ArrayList<String>) repository.findAllUniqueNames();
		assertThat(names.size()).isGreaterThan(0);

		//make sure values are unique
		assertThat(names).doesNotHaveDuplicates();
		for (String name: names){
			System.out.println("activity_name: "+name);
		}
	}

	@Test
	public void testGetUniqueActivityNamesFromDbUsingRestController(){
		String url="/get/activity/names";

		ResponseEntity<ArrayList> responseEntity = restTemplate
				.getForEntity(url, ArrayList.class);

		assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
		ArrayList<String> names = (ArrayList<String>) responseEntity.getBody();
		assertThat(names.size()).isGreaterThan(0);

		//make sure values are unique
		assertThat(names).doesNotHaveDuplicates();
		for (String name: names){
			System.out.println("activity_name: "+name);
		}
	}


	@Test
	public void testCreateActivityDTO(){
		ActivityInputDTO testActivity = new ActivityInputDTO(name,startTimeStamp,endTimeStamp);

		assertEquals(name,testActivity.getName());
		assertEquals(startTimeStamp,testActivity.getStartTimeStamp());
		assertEquals(endTimeStamp,testActivity.getEndTimeStamp());
	}

	@Test
	public void testPostActivityWithDTO(){

		String url="/create-dto";
		ActivityInputDTO testActivityInputDto = new ActivityInputDTO(name,startTimeStamp,endTimeStamp);
		ResponseEntity<Activity> responseEntity = restTemplate
				.postForEntity(
						url,
						testActivityInputDto,
						Activity.class);

		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

		Activity activity = responseEntity.getBody();
		assertThat(activity).isNotEqualTo(null);

		assertActivityFields(activity,name,startTimeStamp,endTimeStamp);
	}

	/**
	 * Assert field values of actual activity match expected activity
	 * @param expected Activity object with expected values
	 * @param actual Actual activity object with values to be checked
	 */
	public void assertActivityFieldsEqual(Activity expected,Activity actual){
		assertEquals(expected.getName(),actual.getName());
		assertEquals(expected.getStartTimeStamp(),actual.getStartTimeStamp());
		assertEquals(expected.getEndTimeStamp(),actual.getEndTimeStamp());
	}

	public void assertActivityFields(Activity activity,String name,String startTimeStamp,String endTimeStamp){
		assertEquals(name,activity.getName());
		assertEquals(startTimeStamp,activity.getStartTimeStamp());
		assertEquals(endTimeStamp,activity.getEndTimeStamp());
	}


}
