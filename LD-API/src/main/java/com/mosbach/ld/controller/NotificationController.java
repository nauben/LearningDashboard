package com.mosbach.ld.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mosbach.ld.dataManager.NotificationDataManager;

@RestController
@RequestMapping("/api/v0.1a")
public class NotificationController {

	private NotificationDataManager dataManager;
	
	@Autowired
	public NotificationController( @Qualifier("n-postgres") NotificationDataManager dataManager) {
		this.dataManager = dataManager;
	}
	
	
	@GetMapping("/notifications")
	public ResponseEntity<?> getAllNotifications() {
		UUID id = (UUID) SecurityContextHolder.getContext().getAuthentication().getDetails();
		return ResponseEntity.ok(dataManager.getAllNotificationsOf(id));
	}
}
