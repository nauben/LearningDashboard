package com.mosbach.ld.model.task;

import com.mosbach.ld.dataManager.TaskManager;
import com.mosbach.ld.dataManagerImpl.PostgresTaskManagerImpl;
import com.mosbach.ld.dataManagerImpl.PropertyFileTaskManagerImpl;

import java.util.Collection;

public class TaskList {
	
	private Student student;
	private Collection<Task> tasks;

	public TaskList() { }

	public TaskList(Student student) {
		this.student = student;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Collection<Task> getTasks() {
		return tasks;
	}

	public void setTasks() {
		// TaskManager taskManager = PropertyFileTaskManagerImpl.getPropertyFileTaskManagerImpl("src/main/resources/TaskList.properties");
		TaskManager taskManager = PostgresTaskManagerImpl.getPostgresTaskManagerImpl();
		tasks = taskManager.getAllTasks(new Student("me", "me"));
	}

	public void addTask(Task task) {
		//TaskManager taskManager = PropertyFileTaskManagerImpl.getPropertyFileTaskManagerImpl("src/main/resources/TaskList.properties");
		TaskManager taskManager = PostgresTaskManagerImpl.getPostgresTaskManagerImpl();
		taskManager.addTask(task, new Student("me", "me"));

	}

}
