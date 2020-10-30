package com.mosbach.ld.model.task;

import java.time.LocalDateTime;
import java.util.Collection;

public class TaskList {
	
	private Collection<Task> tasks;
	private LocalDateTime timeStamp;
	
	public TaskList() {
		super();
	}

	public TaskList(Collection<Task> tasks, LocalDateTime timeStamp) {
		super();
		this.tasks = tasks;
		this.timeStamp = timeStamp;
	}

	public Collection<Task> getTasks() {
		return tasks;
	}

	public void setTasks(Collection<Task> tasks) {
		this.tasks = tasks;
	}

	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(LocalDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}

}
