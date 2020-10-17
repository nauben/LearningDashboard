package com.mosbach.ld.model.dhbwSchedule;

import java.util.Date;

public class DHBWLecture {

	String summary; // welche
	String description; // wer
	String location; // raum
	Date start; // start
	Date end; // ende
	
	public DHBWLecture() {
		super();
	}

	public DHBWLecture(String summary, String description, String location, Date start, Date end) {
		super();
		this.summary = summary;
		this.description = description;
		this.location = location;
		this.start = start;
		this.end = end;
	}
	
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	
}
