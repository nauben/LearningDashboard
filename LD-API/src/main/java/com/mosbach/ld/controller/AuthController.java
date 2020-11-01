package com.mosbach.ld.controller;

import com.mosbach.ld.dataManager.UserDataManager;
import com.mosbach.ld.model.auth.AuthenticationRequest;
import com.mosbach.ld.model.auth.AuthenticationResponse;
import com.mosbach.ld.model.common.DefaultActionResponse;
import com.mosbach.ld.model.user.User;
import com.mosbach.ld.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v0.1a")
public class AuthController {

	private UserDataManager userManager;
	private AuthenticationManager authenticationManager;
	private JwtUtil jwtTokenUtil;
	
	@Autowired
	public AuthController(
				@Qualifier("u-postgres") UserDataManager userManager,
				PasswordEncoder passwordEncoder,
				AuthenticationManager authenticationManager,
				JwtUtil jwtTokenUtil
			) {
		
		this.userManager = userManager;
		this.authenticationManager = authenticationManager;
		this.jwtTokenUtil = jwtTokenUtil;
	}
	
	@PostMapping(
            path = "/login",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
		System.out.println("Hello. "+authenticationRequest.getEmail());
		try {
			authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword())	
			);
		} catch(BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}
		final UserDetails userDetails = userManager
				.loadUserByUsername(authenticationRequest.getEmail());
		final String jwt = jwtTokenUtil.generateToken(userDetails);
		try { userManager.updateUserLogin(userManager.getUUIDByEmail(authenticationRequest.getEmail())); }catch(Exception e) {}
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}
	
	
	@PostMapping( path = "/logout")
	public ResponseEntity<?> logout(@RequestHeader(name="Authorization") String token){
		userManager.markTokenAsInvalid(token);
		return ResponseEntity.ok(new DefaultActionResponse(true, null));
	}
	
	@PostMapping( 
			path = "/register",
			consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> registerNewUser(@RequestBody User user){
		System.out.println(user);
		if(user.getEmail() != null && user.getPassword() != null) {
			userManager.registerNewUser(user);
		}
		return ResponseEntity.ok(null);
	}
	
	@PostMapping( 
			path = "/checkToken",
			consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> checkToken(){

		return ResponseEntity.ok(null);
	}
	
	
	
}
