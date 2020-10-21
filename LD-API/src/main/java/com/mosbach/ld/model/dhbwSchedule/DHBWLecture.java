package com.mosbach.ld.model.dhbwSchedule;

import java.time.LocalDateTime;

public class DHBWLecture {

	String title; 
	String lecturer; 
	String location; 
	LocalDateTime start; 
	LocalDateTime end; 
	
	public DHBWLecture() {
		super();
	}

	public DHBWLecture(String title, String lecturer, String location, LocalDateTime start, LocalDateTime end) {
		super();
		this.title = title;
		this.lecturer = lecturer;
		this.location = location;
		this.start = start;
		this.end = end;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLecturer() {
		return lecturer;
	}

	public void setLecturer(String lecturer) {
		this.lecturer = lecturer;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public LocalDateTime getStart() {
		return start;
	}

	public void setStart(LocalDateTime start) {
		this.start = start;
	}

	public LocalDateTime getEnd() {
		return end;
	}

	public void setEnd(LocalDateTime end) {
		this.end = end;
	}
	
}
