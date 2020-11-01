package com.mosbach.ld.controller;

import com.mosbach.ld.dataManager.LearnStatisticsDataManager;
import com.mosbach.ld.dataManager.NotificationDataManager;
import com.mosbach.ld.dataManager.TaskDataManager;
import com.mosbach.ld.dataManager.UserDataManager;
import com.mosbach.ld.model.alexa.AlexaConnectResponse;
import com.mosbach.ld.model.alexa.AlexaRO;
import com.mosbach.ld.model.alexa.OutputSpeechRO;
import com.mosbach.ld.model.alexa.ResponseRO;
import com.mosbach.ld.model.auth.AuthenticationRequest;
import com.mosbach.ld.model.auth.AuthenticationResponse;
import com.mosbach.ld.model.auth.RegistrationRequest;
import com.mosbach.ld.model.auth.RegistrationResponse;
import com.mosbach.ld.model.dhbwSchedule.DHBWCourses;
import com.mosbach.ld.model.dhbwSchedule.DHBWLecture;
import com.mosbach.ld.model.dhbwSchedule.DHBWSchedule;
import com.mosbach.ld.model.learnStatistics.Subject;
import com.mosbach.ld.model.task.Task;
import com.mosbach.ld.model.task.TaskList;
import com.mosbach.ld.model.user.User;
import com.mosbach.ld.services.AlexaService;
import com.mosbach.ld.services.DHBWScheduleService;
import com.mosbach.ld.services.UserService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.util.NumberUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

//necesary?
//@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v0.1a")
public class MappingController {

	private UserDataManager userManager;
	private TaskDataManager taskManager;
	private NotificationDataManager notificationManager;
	private LearnStatisticsDataManager learnStatisticsManager;
	private DHBWScheduleService dhbwScheduleService;
	private AlexaService alexaService;
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	public MappingController(@Qualifier("u-postgres") UserDataManager userManager,
			@Qualifier("t-postgres") TaskDataManager taskManager,
			@Qualifier("n-postgres") NotificationDataManager notificationManager,
			@Qualifier("ls-postgres") LearnStatisticsDataManager learnStatisticsManager,
				DHBWScheduleService dhbwScheduleService,
				AlexaService alexaService,
				PasswordEncoder passwordEncoder
			) {
		
		this.userManager = userManager;
		this.taskManager = taskManager;
		this.notificationManager = notificationManager;
		this.learnStatisticsManager = learnStatisticsManager;
		
		this.dhbwScheduleService = dhbwScheduleService;
		this.alexaService = alexaService;
		this.passwordEncoder = passwordEncoder;
	}
	
	
	
	
	
	/*
	*/
	
	
	@GetMapping("/test")
	public String test() {
		return "Hello World!" + SecurityContextHolder.getContext().getAuthentication();
	}

	/*
	 *******************************************************************************************************
	 */
	
	@GetMapping("/profile")
	public ResponseEntity<?> getUserProfile(){
		SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		//User u = userManager.getUserById(id);
		return null;
	}
	
	@GetMapping("/profile/short")
	public ResponseEntity<?> getUserProfileShort(){
		
		return null;
	}
	
	@PutMapping(
			path = "/profile",
			consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> updateUserProfile(@RequestBody User user){
		//check if user contains all required info
		return null;
	}
	
	@PostMapping( 
			path = "/contacts",
			consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> addUserContact(@RequestBody User user){
		// check if user id is valid
		return null;
	}
	
	@GetMapping("/contacts")
	public ResponseEntity<?> getUserContacts(){
		return ResponseEntity.ok(userManager.getContacts());
	}
	
	@DeleteMapping("/contacts/{id}")
	public ResponseEntity<?> deleteUserContact(@PathVariable("id") String id){
		//check if contact exists
		//delete contact
		return null;
	}
	
	
	
	/*
	 *******************************************************************************************************
	 */
	
	@GetMapping("/tasks")
	public ResponseEntity<?> getAllTasks() {
		return ResponseEntity.ok(new TaskList(taskManager.getAllTasks(), LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)));
	}
	
	@GetMapping("/tasks/{id}")
	public ResponseEntity<?> getTaskById(@PathVariable("id") String id) {
		if(taskManager.taskExists(UUID.fromString(id)))
			return ResponseEntity.ok(taskManager.getTaskById(UUID.fromString(id)));
		return null;
	}
	
	@PostMapping(
            path = "/tasks",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
	public ResponseEntity<?> createTask(@RequestBody Task task) {
		if(task.getSwimlane() != null && task.getTitle() != null)
			taskManager.createNewTask(task);
		return null;
	}
	
	@PutMapping(
            path = "/tasks/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
	public ResponseEntity<?> updateTask(@PathVariable("id") String id, @RequestBody Task task) {
		if(task.getId() != null && task.getSwimlane() != null && task.getTitle() != null)
			taskManager.updateTask(task);
		return null;
	}
	
	@DeleteMapping(
            path = "/tasks/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
	public ResponseEntity<?> deleteTask(@PathVariable("id") String id) {
		
		return null;
	}
	
	@DeleteMapping(
            path = "/tasks",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
	public ResponseEntity<?> deleteAllTasks() {
		
		return null;
	}
	
	/*
	 *******************************************************************************************************
	 */
	
	@GetMapping("/notifications")
	public ResponseEntity<?> getAllNotifications() {
		return ResponseEntity.ok(notificationManager.getAllNotifications());
	}
	
	/*
	 *******************************************************************************************************
	 */
	
	@PostMapping(
            path = "/pomodoro/subjects",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
	public ResponseEntity<?> addSubject(@RequestBody Subject subject) {
		//check if valid
		return null;
	}
	
	@PutMapping(
            path = "/pomodoro/subjects/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
	public ResponseEntity<?> updateSubjectName(@PathVariable("id") String id, @RequestBody Subject subject) {
		//check if valid
		return null;
	}
	
	@PutMapping(
            path = "/pomodoro/subjects/{id}/time",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
	public ResponseEntity<?> updateSubjectTime(@PathVariable("id") String id, @RequestBody Subject subject) {
		//check if valid
		return null;
	}
	
	@GetMapping("/pomodoro/subjects")
	public ResponseEntity<?> getAllSubjects(){
		return null;
	}
	
	@GetMapping("/pomodoro/subjects/{id}")
	public ResponseEntity<?> getSubject(@PathVariable("id") String id){
		return null;
	}
	
	@DeleteMapping(
            path = "/pomodoro/subjects/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
	public ResponseEntity<?> deleteSubject(@PathVariable("id") String id) {
		
		return null;
	}
	
	
	/*
	 *******************************************************************************************************
	 */
	
	
	@PostMapping(
            path = "/alexa",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
	public AlexaRO alexaEndPoint(@RequestBody AlexaRO alexaRO) {
		
		return alexaService.processRequest(alexaRO);
	}
	
	@PostMapping(
            path = "/alexa/connect/token",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
	public AlexaConnectResponse accountLinkingAlexaToken(final @RequestParam Map<String, String> parameters) {
		return null;
	}
	
	@PostMapping(
            path = "/alexa/connect/auth",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
	public AlexaConnectResponse accountLinkingAlexaAuth() {
		return null;
	}
	
	/*
	 *******************************************************************************************************
	 */
	
	@PostMapping(
            path = "/registrate",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
	public ResponseEntity<?> registrateNewUser(@RequestBody User user) {
		if(user.getEmail() != null && user.getPassword() != null)
			userManager.registerNewUser(user);
		return null;
	}
	
	@PostMapping(
            path = "/activate/{token}",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
	public ResponseEntity<?> registrateNewUser() {
		//userService.
		return null;
	}
	
	/*
	 *******************************************************************************************************
	 */
	
	@GetMapping("/dhbw-schedule/courses")
    public DHBWCourses getAllCourses() {
		return new DHBWCourses(dhbwScheduleService.loadAllCourses());
    }
	
	@GetMapping("/dhbw-schedule/{course}")
    public DHBWSchedule getAllLecturesByCourse(@PathVariable("course") String course, @RequestParam(value = "name", defaultValue = "Student") String name) {
		Collection<DHBWLecture> lectures = dhbwScheduleService.loadAllLecturesOfCourse(course);
        return new DHBWSchedule(course, LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), lectures);
    }
	
	@GetMapping("/dhbw-schedule/{course}/upcomingdays")
    public DHBWSchedule getUpcomingDaysLecturesByCourse(@PathVariable("course") String course, @RequestParam(value = "days", defaultValue = "3") int days) {
		
		Collection<DHBWLecture> lectures = dhbwScheduleService.loadAllLecturesOfCourse(course);
		Predicate<DHBWLecture> streamsPredicate = 
				lecture -> lecture.getEnd().isAfter(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)) 
					&& lecture.getStart().isBefore(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).plusDays(days));
		 
	    lectures = lectures.stream()
	      .filter(streamsPredicate)
	      .collect(Collectors.toList());
		
	    return new DHBWSchedule(course, LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), lectures);
    }
	
	
	

}
