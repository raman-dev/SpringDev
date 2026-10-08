package com.hackerman.activitytracker;

import com.hackerman.activitytracker.activity.Activity;
import com.hackerman.activitytracker.user.UserCreateDTO;
import com.hackerman.activitytracker.activity.repository.ActivityInputDTO;
import com.hackerman.activitytracker.activity.repository.ActivityRepoContainer;
import com.hackerman.activitytracker.activity.repository.ActivityRepository;
import com.hackerman.activitytracker.user.UserRestController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.client.EntityExchangeResult;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.springframework.web.context.WebApplicationContext;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TimeZone;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes = {ActivitytrackerApplication.class},
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestRestTemplate
@AutoConfigureMockMvc
class ActivitytrackerApplicationTests {

	private static final String API_LOGIN_URI = "/api/login";

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private ActivityRepoContainer activityRepoContainer;

	private static final String activityName = "Programming";
	private static final String startTimeStamp = "8am";
	private static final String endTimeStamp = "11am";

	private static final LocalDate date = LocalDate.of(2026,9,15);
	private static final LocalTime startTime = LocalTime.of(16,26);
	private static final LocalTime endTime = LocalTime.of(17,26);
	private static final TimeZone timeZone = TimeZone.getDefault();

	private static final String unusedEmail = "user@example.com";
	private static final String userEmail = "raman@example.com";

	private static final String userPassword = "password";
	private static final String matchingPassword = "password";
	
	private static final String SERVER_URL = "http://localhost:2020";
	private static final String GET_ACTIVITY_BY_ID_OWNED_BY_USER = "/get/activity/90";
	private static final String GET_ACTIVITY_BY_ID_NOT_OWNED_BY_USER = "/get/activity/93";

	@Autowired
	private WebApplicationContext webApplicationContext;

	//THIS DOES NOT PERFORM END TO END TESTS NO ACTUALLY SERVER OR REQUEST IS SENT
	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper objectMapper;

//	@Test
//	@Disabled
//	public void testPostActivity(){
//
//		String url = "/create-activity";
//		Activity testActivity = new Activity(activityName,startTimeStamp,endTimeStamp);
//		ResponseEntity<Activity> responseEntity = restTemplate
//				.postForEntity(url,testActivity,Activity.class);
//
//		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//		Activity activity = responseEntity.getBody();
//
//		assertActivityFields(activity, activityName,startTimeStamp,endTimeStamp);
//	}

	@Test
	public void sampleTest(){
		System.out.println("Sample Test test");
		assertEquals(1,1);
	}

	@Test
	public void testActivityCreation(){
		Activity activity = new Activity(activityName,startTimeStamp,endTimeStamp,null);
		System.out.println("Created:\n\t" + activity);

		assertActivityFields(activity, activityName,startTimeStamp,endTimeStamp);
	}

//	@Test
//	public void testActivityDatabaseCreationWithRepository(){
//		Activity testActivity = new Activity(activityName,startTimeStamp,endTimeStamp, );
//		ActivityRepository activityRepository = activityRepoContainer.getRepository();
//		activityRepository.save(testActivity);
//
//		ArrayList<Activity> list = (ArrayList<Activity>)activityRepository.findByName(testActivity.getName());
//
//		assertThat(list.size()).isGreaterThan(0);
//
//		Activity savedActivity = list.get(0);
//
//		assertActivityFieldsEqual(testActivity,savedActivity);
//	}

//	@Test
//	@Disabled
//	public void testActivityDbCreationWithPostRestMapping(){
//		String url="/create";
//		Activity testActivity = new Activity(name,startTimeStamp,endTimeStamp);
//		ResponseEntity<Activity> responseEntity = restTemplate
//				.postForEntity(url,testActivity,Activity.class);
//
//		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//		//check database
//		Activity dbActivity = activityRepoContainer.getRepository().findByName(testActivity.getName()).getFirst();
//
//		assertActivityFieldsEqual(testActivity,dbActivity);
//	}

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


//	@Test
//
//	public void testCreateActivityDTO(){
//		ActivityInputDTO testActivity = new ActivityInputDTO(name,startTimeStamp,endTimeStamp);
//
//		assertEquals(name,testActivity.getName());
//		assertEquals(startTimeStamp,testActivity.getStartTimeStamp());
//		assertEquals(endTimeStamp,testActivity.getEndTimeStamp());
//	}
//
//	@Test
//	public void testCreateActivityDTOWithDateTime(){
//		ActivityInputDTO testActivity = new ActivityInputDTO(name,startTimeStamp,endTimeStamp,date,time);
//
//		assertEquals(name,testActivity.getName());
//		assertEquals(startTimeStamp,testActivity.getStartTimeStamp());
//		assertEquals(endTimeStamp,testActivity.getEndTimeStamp());
//
//		assertEquals(date,testActivity.getDate());
//		assertEquals(time,testActivity.getTime());
//	}

//	@Test
//	public void testPostActivityWithDTO(){
//
//		String url="/create-dto";
//		ActivityInputDTO testActivityInputDto = new ActivityInputDTO(name,startTimeStamp,endTimeStamp);
//		ResponseEntity<Activity> responseEntity = restTemplate
//				.postForEntity(
//						url,
//						testActivityInputDto,
//						Activity.class);
//
//		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//		Activity activity = responseEntity.getBody();
//		assertThat(activity).isNotEqualTo(null);
//
//		assertActivityFields(activity,name,startTimeStamp,endTimeStamp);
//	}


	@Test
	public void testCreateActivityInputDTO(){
		ActivityInputDTO activityInputDTO = new ActivityInputDTO(activityName,date,startTime,endTime,timeZone);
		assertActivityInputDTOFields(activityInputDTO, activityName,date,startTime,endTime,timeZone);
	}

	@Test
	public void testUserCreateDTO(){
		UserCreateDTO userCreateDTO = new UserCreateDTO(unusedEmail, userPassword,matchingPassword);

		assertEquals(unusedEmail,userCreateDTO.getEmail());
		assertEquals(userPassword,userCreateDTO.getPassword());
		assertEquals(matchingPassword,userCreateDTO.getMatchingPassword());
	}


	@Test
	public void signupAndCreateUserApiTest(){
		UserCreateDTO userCreateDTO = new UserCreateDTO(unusedEmail, userPassword,matchingPassword);
		final String uri = "/signup";

		ResponseEntity responseEntity = restTemplate
				.postForEntity(uri,userCreateDTO,Object.class);
		assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
		//read the string message
		System.out.println("------START RESPONSE--------");
		Map<String,String> map = (Map<String, String>) responseEntity.getBody();
		map.forEach((k,v) -> System.out.println(k+":\n\t"+ v));
		System.out.println("------END RESPONSE----------");
	}

	@Test
	public void formLoginTest(){
		//test user login how?
		final String url = "/login";
		try {
			mvc.perform(formLogin(url).user(userEmail).password(userPassword));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	@WithMockUser(username=userEmail,password=userPassword)
	public void mockUserCreateActivityTest() throws Exception {
		//test user login how?
		final String uri = "/create/activity";
		ActivityInputDTO activityInputDTO = new ActivityInputDTO(
				activityName,
				date, startTime,endTime,
				timeZone);
		mvc.perform(
				post(uri)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(activityInputDTO))
				)
				.andExpect(status().isCreated())//CREATED 201
				.andExpect(header().exists("Location"));//Location header is in results
	}


	//use to map response body to activity type shit
	record ActivityOut (Long entityId,String name,String startTimeStamp,String endTimeStamp){};

	@Test 
	public void loginAndGetActivity(){
		RestTestClient client = RestTestClient.bindToServer().baseUrl(SERVER_URL).build();
		EntityExchangeResult<LinkedHashMap> loginResponse = client
				.post()
				.uri(API_LOGIN_URI)
				.contentType(MediaType.APPLICATION_JSON)
				.body(objectMapper.writeValueAsString(new UserRestController.LoginData(
						userEmail, userPassword))
				).exchange()
				.returnResult(LinkedHashMap.class);

		HttpHeaders headers = loginResponse.getResponseHeaders();
		assertThat(headers.isEmpty()).isEqualTo(false);

		String sessionCookie = headers.get(HttpHeaders.SET_COOKIE).getFirst();
		System.out.println("SESSION_COOKIE: "+sessionCookie);
		assertThat(sessionCookie.contains("JSESSIONID=")).isEqualTo(true);

		LinkedHashMap map = loginResponse.getResponseBody();
		System.out.println("\n------START RESPONSE--------");
		map.forEach((k,v) -> System.out.println(k+":\n\t"+ v));
		System.out.println("------END RESPONSE----------");

		EntityExchangeResult<ActivityOut> getResponse = client
				.get()
				.uri(GET_ACTIVITY_BY_ID_OWNED_BY_USER)
				.header(HttpHeaders.COOKIE,sessionCookie)
				.exchange()
				.expectStatus().isOk()
				.returnResult(ActivityOut.class);//objectmapper will map the json to this pojo

		ActivityOut activityOut = getResponse.getResponseBody();
		assertThat(activityOut).isNotEqualTo(null);

		System.out.println("\n------START RESPONSE--------");
		System.out.println(activityOut);
		System.out.println("------END RESPONSE----------");
	}

	public void assertActivityInputDTOFields(ActivityInputDTO activityInputDTO,String name,LocalDate date,LocalTime a,LocalTime b,TimeZone timeZone){
		assertEquals(name,activityInputDTO.getName());
		assertEquals(date,activityInputDTO.getDate());
		assertEquals(a,activityInputDTO.getStartTime());
		assertEquals(b,activityInputDTO.getEndTime());
		assertEquals(timeZone,activityInputDTO.getTimeZone());
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
