package com.hackerman.activitytracker;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

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
	public void sampleTest(){
		System.out.println("Sample Test test");
		assertEquals(1,1);
	}

	@Test
	public void testActivityCreation(){

		Activity activity = new Activity(name,startTimeStamp,endTimeStamp);

		System.out.println("Created:\n\t" + activity);

		assertEquals(activity.getName(),name);
		assertEquals(activity.getStartTimeStamp(),startTimeStamp);
		assertEquals(activity.getEndTimeStamp(),endTimeStamp);

	}

	@Test
	public void testPostActivity(){

		String url = "/create-activity";
		Activity testActivity = new Activity(name,startTimeStamp,endTimeStamp);
		ResponseEntity<Activity> responseEntity = restTemplate
				.postForEntity(url,testActivity,Activity.class);

		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		Activity activity = responseEntity.getBody();

		assertEquals(activity.getName(),name);
		assertEquals(activity.getStartTimeStamp(),startTimeStamp);
		assertEquals(activity.getEndTimeStamp(),endTimeStamp);
	}

	@Test
	public void testActivityDatabaseCreationWithRepository(){
		Activity testActivity = new Activity(name,startTimeStamp,endTimeStamp);
		ActivityRepository activityRepository = activityRepoContainer.getRepository();
		activityRepository.save(testActivity);

		ArrayList<Activity> list = (ArrayList<Activity>)activityRepository.findByName(testActivity.getName());

		assertThat(list.size()).isGreaterThan(0);

		Activity savedActivity = list.get(0);

		assertEquals(savedActivity.getName(),testActivity.getName());
		assertEquals(savedActivity.getStartTimeStamp(),testActivity.getStartTimeStamp());
		assertEquals(savedActivity.getEndTimeStamp(),testActivity.getEndTimeStamp());
	}

	@Test
	public void testActivityDbCreationWithPostRestMapping(){
		String url="/create-activity-jpa";
		Activity testActivity = new Activity(name,startTimeStamp,endTimeStamp);
		ResponseEntity<Activity> responseEntity = restTemplate
				.postForEntity(url,testActivity,Activity.class);

		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		//check database
		Activity dbActivity = activityRepoContainer.getRepository().findByName(testActivity.getName()).getFirst();

		assertEquals(dbActivity.getName(),testActivity.getName());
		assertEquals(dbActivity.getStartTimeStamp(),testActivity.getStartTimeStamp());
		assertEquals(dbActivity.getEndTimeStamp(),testActivity.getEndTimeStamp());
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




}
