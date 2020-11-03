package com.mosbach.ld.model.dhbwSchedule;

import java.time.LocalDateTime;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DHBWSchedule {

	String course;
	LocalDateTime datestamp;
	Collection<DHBWLecture> lectures;
	
	public DHBWSchedule() {
		super();
	}
	
	public DHBWSchedule(String course, LocalDateTime datestamp, Collection<DHBWLecture> lectures) {
		super();
		this.course = course;
		this.datestamp = datestamp;
		this.lectures = lectures;
	}
	
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public LocalDateTime getDatestamp() {
		return datestamp;
	}
	public void setDatestamp(LocalDateTime datestamp) {
		this.datestamp = datestamp;
	}
	public Collection<DHBWLecture> getLectures() {
		return lectures;
	}
	public void setLectures(Collection<DHBWLecture> lectures) {
		this.lectures = lectures;
	}
	
	
}
