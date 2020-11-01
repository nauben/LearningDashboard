package com.mosbach.ld.model.task;

import java.time.LocalDateTime;
import java.util.UUID;

import com.mosbach.ld.model.user.User;

public class Comment {

	private UUID id;
	private LocalDateTime timeStamp;
	private String message;
	private User user;
	
	public Comment() {
		super();
	}

	public Comment(UUID id, LocalDateTime timeStamp, String message, User user) {
		super();
		this.id = id;
		this.timeStamp = timeStamp;
		this.message = message;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(LocalDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
