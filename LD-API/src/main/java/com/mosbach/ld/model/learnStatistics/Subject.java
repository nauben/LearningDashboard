package com.mosbach.ld.model.learnStatistics;

import java.time.LocalDateTime;
import java.util.UUID;

public class Subject {

	private UUID id;
	private String title;
	private long time;
	private LocalDateTime since;
	
	public Subject() {
		super();
	}

	public Subject(UUID id, String title, long time, LocalDateTime since) {
		super();
		this.id = id;
		this.title = title;
		this.time = time;
		this.since = since;
	}

	public UUID getSubject() {
		return id;
	}

	public void setSubject(UUID id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public LocalDateTime getSince() {
		return since;
	}

	public void setSince(LocalDateTime since) {
		this.since = since;
	}
	
}
