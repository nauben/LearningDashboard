package com.mosbach.ld.model.dhbwSchedule;

import java.util.Collection;
import java.util.Date;

public class DHBWSchedule {

	String course;
	Date datestamp;
	Collection<DHBWLecture> lectures;
	
	public DHBWSchedule() {
		super();
	}
	
	public DHBWSchedule(String course, Date datestamp, Collection<DHBWLecture> lectures) {
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
	public Date getDatestamp() {
		return datestamp;
	}
	public void setDatestamp(Date datestamp) {
		this.datestamp = datestamp;
	}
	public Collection<DHBWLecture> getLectures() {
		return lectures;
	}
	public void setLectures(Collection<DHBWLecture> lectures) {
		this.lectures = lectures;
	}
	
	
}
