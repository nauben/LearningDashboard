package com.mosbach.ld.model.task;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

import com.mosbach.ld.model.user.User;

public class Task {

	private UUID id;
	private Integer swimlane;
	private String title;
	private String description;
	private LocalDateTime dueDate;
	private Collection<User> assignees;
	private LocalDateTime created;
	private User createdBy;
	private LocalDateTime updated;
	private User updatedBy;
	private Collection<Comment> comments;
	private Collection<Activity> activities;
	private Collection<CheckItem> checklist;
	private Integer label;
	
	public Task() {
		super();
	}

	public Task(UUID id, int swimlane, String title, String description, LocalDateTime dueDate,
			Collection<User> assignees, LocalDateTime created, User createdBy, LocalDateTime updated, User updatedBy,
			Collection<Comment> comments, Collection<Activity> activities, Collection<CheckItem> checklist, int label) {
		super();
		this.id = id;
		this.swimlane = swimlane;
		this.title = title;
		this.description = description;
		this.dueDate = dueDate;
		this.assignees = assignees;
		this.created = created;
		this.createdBy = createdBy;
		this.updated = updated;
		this.updatedBy = updatedBy;
		this.comments = comments;
		this.activities = activities;
		this.checklist = checklist;
		this.label = label;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Integer getSwimlane() {
		return swimlane;
	}

	public void setSwimlane(int swimlane) {
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

	public LocalDateTime getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDateTime dueDate) {
		this.dueDate = dueDate;
	}

	public Collection<User> getAssignees() {
		return assignees;
	}

	public void setAssignees(Collection<User> assignees) {
		this.assignees = assignees;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDateTime getUpdated() {
		return updated;
	}

	public void setUpdates(LocalDateTime updated) {
		this.updated = updated;
	}

	public User getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
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

	public Integer getLabel() {
		return label;
	}

	public void setLabel(int label) {
		this.label = label;
	}
	
}
