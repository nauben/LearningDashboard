package com.mosbach.ld.model.task;

import java.util.Collection;
import java.util.Date;

public class Task {

	private int id;
	private short swimlane;
	private String title;
	private String description;
	private Date dueDate;
	private Date created;
	private Date updated;
	private Collection<Comment> comments;
	private Collection<Activity> activities;
	private Collection<CheckItem> checklist;
	private Collection<Member> members;
	private TaskLabel label;
	
	public Task() {
		
	}
	
	public Task(int id, short swimlane, String title, String description, Date dueDate, Date created, Date updated,
			Collection<Comment> comments, Collection<Activity> activities, Collection<CheckItem> checklist,
			Collection<Member> members, TaskLabel label) {
		super();
		this.id = id;
		this.swimlane = swimlane;
		this.title = title;
		this.description = description;
		this.dueDate = dueDate;
		this.created = created;
		this.updated = updated;
		this.comments = comments;
		this.activities = activities;
		this.checklist = checklist;
		this.members = members;
		this.label = label;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public short getSwimlane() {
		return swimlane;
	}


	public void setSwimlane(short swimlane) {
		this.swimlane = swimlane;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public Date getDueDate() {
		return dueDate;
	}


	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}


	public Date getCreated() {
		return created;
	}


	public void setCreated(Date created) {
		this.created = created;
	}


	public Date getUpdated() {
		return updated;
	}


	public void setUpdated(Date updated) {
		this.updated = updated;
	}


	public Collection<Comment> getComments() {
		return comments;
	}


	public void setComments(Collection<Comment> comments) {
		this.comments = comments;
	}


	public Collection<Activity> getActivities() {
		return activities;
	}


	public void setActivities(Collection<Activity> activities) {
		this.activities = activities;
	}


	public Collection<CheckItem> getChecklist() {
		return checklist;
	}


	public void setChecklist(Collection<CheckItem> checklist) {
		this.checklist = checklist;
	}


	public Collection<Member> getMembers() {
		return members;
	}


	public void setMembers(Collection<Member> members) {
		this.members = members;
	}


	public TaskLabel getLabel() {
		return label;
	}


	public void setLabel(TaskLabel label) {
		this.label = label;
	}	
	
}
