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

import io.jsonwebtoken.Jwts;

import java.time.LocalDate;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.NumberUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v0.1a")
public class AuthController {

	private UserDataManager userManager;
	private TaskDataManager taskManager;
	private NotificationDataManager notificationManager;
	private LearnStatisticsDataManager learnStatisticsManager;
	private DHBWScheduleService dhbwScheduleService;
	private AlexaService alexaService;
	private PasswordEncoder passwordEncoder;
	private AuthenticationManager authenticationManager;
	
	@Autowired
	public AuthController(@Qualifier("u-postgres") UserDataManager userManager,
			@Qualifier("t-postgres") TaskDataManager taskManager,
			@Qualifier("n-postgres") NotificationDataManager notificationManager,
			@Qualifier("ls-postgres") LearnStatisticsDataManager learnStatisticsManager,
				DHBWScheduleService dhbwScheduleService,
				AlexaService alexaService,
				PasswordEncoder passwordEncoder,
				AuthenticationManager authenticationManager
			) {
		
		this.userManager = userManager;
		this.taskManager = taskManager;
		this.notificationManager = notificationManager;
		this.learnStatisticsManager = learnStatisticsManager;
		
		this.dhbwScheduleService = dhbwScheduleService;
		this.alexaService = alexaService;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
	}
	
	@PostMapping( 
			path = "/login",
			consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
		
		try {
			authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword())	
			);
		} catch(BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}
		
		final UserDetails userDetails = userManager
				.loadUserByUsername(authenticationRequest.getEmail());
		
		final String jwt = generateToken(userDetails);
		
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}
	
	private String successfulAuthentication(Authentication authResult) {
		return Jwts.builder()
        .setSubject(authResult.getName())
        .claim("authorities", authResult.getAuthorities())
        .setIssuedAt(new Date())
        .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(jwtConfig.getTokenExpirationAfterDays())))
        .signWith(secretKey)
        .compact();
	}
	
	@PostMapping( path = "/logout")
	public ResponseEntity<?> logout(){
		return null;
	}
	
	@PostMapping( 
			path = "/register",
			consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> registerNewUser(@RequestBody User user){
		return null;
	}
	
}
