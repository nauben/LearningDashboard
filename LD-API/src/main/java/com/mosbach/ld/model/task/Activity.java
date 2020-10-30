package com.mosbach.ld.model.task;

import java.time.LocalDateTime;
import java.util.UUID;

import com.mosbach.ld.model.user.User;

public class Activity {

	private UUID id;
	private User actor;
	private LocalDateTime timeStamp;
	private String description;
	
	public Activity() {
		super();
	}

	public Activity(UUID id, User actor, LocalDateTime timeStamp, String description) {
		super();
		this.id = id;
		this.actor = actor;
		this.timeStamp = timeStamp;
		this.description = description;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public User getActor() {
		return actor;
	}

	public void setActor(User actor) {
		this.actor = actor;
	}

	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(LocalDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
