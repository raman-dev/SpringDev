package com.hackerman.activitytracker;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
	private MyRepository myRepoContainer;

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
	public void testGetActivity(){
		String url = "/get-activity";
//		Activity testActivity = new Activity(name,startTimeStamp,endTimeStamp);
		ResponseEntity<Activity> responseEntity = restTemplate.getForEntity(url,Activity.class);

		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		Activity activity = responseEntity.getBody();

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
		myRepoContainer.save(testActivity);

		ArrayList<Activity> list = (ArrayList<Activity>)myRepoContainer.repository.findByName(testActivity.getName());

		assertThat(list.size()).isGreaterThan(0);

		Activity savedActivity = list.get(0);


		assertEquals(savedActivity.getName(),testActivity.getName());
		assertEquals(savedActivity.getStartTimeStamp(),testActivity.getStartTimeStamp());
		assertEquals(savedActivity.getEndTimeStamp(),testActivity.getEndTimeStamp());
	}
}
