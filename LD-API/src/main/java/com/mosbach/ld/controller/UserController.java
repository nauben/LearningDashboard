package com.mosbach.ld.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mosbach.ld.dataManager.UserDataManager;
import com.mosbach.ld.model.common.DefaultActionResponse;
import com.mosbach.ld.model.user.User;

@RestController
@RequestMapping("/api/v0.1a")
public class UserController {

	private UserDataManager userManager;
	
	@Autowired
	public UserController(@Qualifier("u-postgres") UserDataManager userManager) {
		this.userManager = userManager;
	}
	
	@GetMapping("/profile")
	public ResponseEntity<?> getUserProfile(){
		UUID id = (UUID) SecurityContextHolder.getContext().getAuthentication().getDetails();
		User u = userManager.getUserById(id);
		return ResponseEntity.ok(u);
	}
	
	@GetMapping("/profile/short")
	public ResponseEntity<?> getUserProfileShort(){
		UUID id = (UUID) SecurityContextHolder.getContext().getAuthentication().getDetails();
		User u = userManager.getUserById(id);
		return ResponseEntity.ok(shortMaskUser(u));
	}
	private User shortMaskUser(User user) {
		user.setAccountNonExpired(null);
		user.setAccountNonLocked(null);
		user.setContacts(null);
		user.setCreated(null);
		user.setCredentialsNonExpired(null);
		user.setEnabled(null);
		user.setGender(null);
		user.setGrantedAuthorities(null);
		user.setIsAccountNonExpired(null);
		user.setIsAccountNonLocked(null);
		user.setIsCredentialsNonExpired(null);
		user.setIsEnabled(null);
		user.setPassword(null);
		user.setSendNotifications(null);
		user.setVisibility(null);
		return user;
	}
	
	@PutMapping(
			path = "/profile",
			consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> updateUserProfile(@RequestBody User user){
		UUID id = (UUID) SecurityContextHolder.getContext().getAuthentication().getDetails();
		boolean success = false;
		if(user.getId() != null && user.getId().equals(id)) {
			success = userManager.updateUser(user);
		}else {
			return ResponseEntity.ok(new DefaultActionResponse(false, "ID is required!"));
		}
		if(success)
			return ResponseEntity.ok(new DefaultActionResponse(true, null));
		return ResponseEntity.ok(new DefaultActionResponse(false, "Error while saving data..."));
	}
	
	@PostMapping( 
			path = "/contacts",
			consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> addUserContact(@RequestBody User user){
		UUID id = (UUID) SecurityContextHolder.getContext().getAuthentication().getDetails();
		boolean success = false;
		if(user.getId() != null) {
			success = userManager.setNewContactOf(id, user.getId());
		}else {
			return ResponseEntity.ok(new DefaultActionResponse(false, "ID is required!"));
		}
		if(success)
			return ResponseEntity.ok(new DefaultActionResponse(true, null));
		return ResponseEntity.ok(new DefaultActionResponse(false, "Error while saving data..."));
	}
	
	@GetMapping("/contacts")
	public ResponseEntity<?> getUserContacts(){
		UUID id = (UUID) SecurityContextHolder.getContext().getAuthentication().getDetails();
		try {
			userManager.getContactsOf(id);
			return ResponseEntity.ok(userManager.getContactsOf(id));
		}catch(Exception e) {
			return ResponseEntity.ok(new DefaultActionResponse(false, "Error while retrieving data..."));
		}
	}
	
	@DeleteMapping("/contacts/{id}")
	public ResponseEntity<?> deleteUserContact(@PathVariable("id") String cid){
		UUID id = (UUID) SecurityContextHolder.getContext().getAuthentication().getDetails();
		UUID contactId = UUID.fromString(cid);
		boolean success = false;
		if(userManager.userExists(contactId)) {
			success = userManager.deleteContactFrom(id, contactId);
		}else {
			return ResponseEntity.ok(new DefaultActionResponse(false, "No such user exists..."));
		}
		if(success)
			return ResponseEntity.ok(new DefaultActionResponse(true, null));
		return ResponseEntity.ok(new DefaultActionResponse(false, "Error while saving data..."));
	}
}
