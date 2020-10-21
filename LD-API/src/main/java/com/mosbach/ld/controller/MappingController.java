package com.mosbach.ld.controller;

import com.mosbach.ld.dataManagerImpl.PostgresTaskManagerImpl;
import com.mosbach.ld.model.alexa.AlexaRO;
import com.mosbach.ld.model.alexa.OutputSpeechRO;
import com.mosbach.ld.model.alexa.ResponseRO;
import com.mosbach.ld.model.auth.AuthenticationRequest;
import com.mosbach.ld.model.auth.AuthenticationResponse;
import com.mosbach.ld.model.dhbwSchedule.DHBWCourses;
import com.mosbach.ld.model.dhbwSchedule.DHBWLecture;
import com.mosbach.ld.model.dhbwSchedule.DHBWSchedule;
import com.mosbach.ld.model.task.Task;
import com.mosbach.ld.model.task.TaskList;
import com.mosbach.ld.services.DHBWScheduleService;
import com.mosbach.ld.services.UserCredentialService;
import com.mosbach.ld.util.JwtUtil;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

//necesary?
//@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v0.1a")
public class MappingController {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserCredentialService userCredentialService;
	@Autowired
	private DHBWScheduleService dhbwScheduleService;
	@Autowired
	private JwtUtil jwtTokenUtil;
	
	@PostMapping(
            path = "/authenticate",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
		try {
			authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword())	
			);
		} catch(BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}
		
		final UserDetails userDetails = userCredentialService
				.loadUserByUsername(authenticationRequest.getEmail());
		
		final String jwt = jwtTokenUtil.generateToken(userDetails);
		
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}
	
	
    // TODO
    // The student is completely ignored.
    //

    // TODO
    // delete, update, get by id, get sorted, ...
    //

    // TODO
    // Set the used DataProvider (ProperyFileManager, PostgresMaganer) here and not in TaskList
    //
	
	@GetMapping("/test")
	public String test() {
		return "Hello World!";
	}

	/*
    @GetMapping("/task/all")
    public TaskList getTasks(@RequestParam(value = "name", defaultValue = "Student") String name) {

        TaskList taskList = new TaskList();
        taskList.setTasks();

        return taskList;
    }



    @PostMapping(
            path = "/task",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    @ResponseStatus(HttpStatus.OK)
    public String createTask(@RequestBody Task task) {

        TaskList taskList = new TaskList();
        taskList.addTask(task);

        return task.getName();
    }


    @PostMapping(
            path = "/task/createtable"
    )
    @ResponseStatus(HttpStatus.OK)
    public String createTask() {

        final PostgresTaskManagerImpl postgresTaskManagerImpl =
                PostgresTaskManagerImpl.getPostgresTaskManagerImpl();
        postgresTaskManagerImpl.createTableTask();

        return "Database Table created";
    }



    @PostMapping(
            path = "/alexa",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public AlexaRO getTasks(@RequestBody AlexaRO alexaRO) {

        String outText = "";

        if (alexaRO.getRequest().getType().equalsIgnoreCase("LaunchRequest")) {
            outText = outText + "Welcome to the Mosbach Task Organizer. ";
        }
        else {
            if (alexaRO.getRequest().getType().equalsIgnoreCase("IntentRequest") &&
                    (alexaRO.getRequest().getIntent().getName().equalsIgnoreCase("TaskReadIntent"))) {
                try
                {
                    TaskList taskList = new TaskList(
                            new Student("me", "ignore")
                    );
                    taskList.setTasks();

                    outText = outText + "You have to do the following tasks. ";
                    int i = 1;
                    for (Task temp : taskList.getTasks()) {
                        outText = outText + "Number " + i + " . ";
                        outText = outText + temp.getName() + ", priority " + temp.getPriority() + " . ";
                        i++;
                    }
                }
                catch(Exception e) {
                    outText = "Unfortunately, we cannot reach heroku. Our REST server is not responding. ";
                }
            }
        }

        return
                prepareResponse(alexaRO, outText, true);
    }

    private AlexaRO prepareResponse(AlexaRO alexaRO, String outText, boolean shouldEndSession) {

        alexaRO.setRequest(null);
        alexaRO.setSession(null);
        alexaRO.setContext(null);
        OutputSpeechRO outputSpeechRO = new OutputSpeechRO();
        outputSpeechRO.setType("PlainText");
        outputSpeechRO.setText(outText);
        ResponseRO response = new ResponseRO(outputSpeechRO, shouldEndSession);
        alexaRO.setResponse(response);
        return alexaRO;
    }
    */
	
	@GetMapping("/dhbw-schedule/courses/all")
    public DHBWCourses getAllCourses() {
		return new DHBWCourses(dhbwScheduleService.loadAllCourses());
    }
	
	@GetMapping("/dhbw-schedule/{course}/all")
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
