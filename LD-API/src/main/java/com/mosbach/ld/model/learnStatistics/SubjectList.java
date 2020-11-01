package com.mosbach.ld.model.learnStatistics;

import java.util.Collection;

public class SubjectList {

	private Collection<Subject> subjects;
	
	public SubjectList() {
		super();
	}

	public SubjectList(Collection<Subject> subjects) {
		super();
		this.subjects = subjects;
	}

	public Collection<Subject> getSubjects() {
		return subjects;
	}

	public void setSubjects(Collection<Subject> subjects) {
		this.subjects = subjects;
	};
	
}
