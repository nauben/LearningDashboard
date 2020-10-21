package com.mosbach.ld.model.dhbwSchedule;

import java.util.Collection;

public class DHBWCourses {

	Collection<String> courses;

	public DHBWCourses() {
		super();
	}

	public DHBWCourses(Collection<String> courses) {
		super();
		this.courses = courses;
	}

	public Collection<String> getCourses() {
		return courses;
	}

	public void setCourses(Collection<String> courses) {
		this.courses = courses;
	}
	
}
