package com.mosbach.ld.model.notification;

import java.time.LocalDateTime;
import java.util.UUID;

public class Notification {

	private UUID id;
	private LocalDateTime timestamp;
	private String message;
	private String link;
	
	public Notification() {
		super();
	}

	public Notification(UUID id, LocalDateTime timestamp, String message, String link) {
		super();
		this.id = id;
		this.timestamp = timestamp;
		this.message = message;
		this.link = link;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
	
}
