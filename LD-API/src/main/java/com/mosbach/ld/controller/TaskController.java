package com.mosbach.ld.controller;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mosbach.ld.dataManager.TaskDataManager;
import com.mosbach.ld.dataManager.UserDataManager;
import com.mosbach.ld.model.common.DefaultActionResponse;
import com.mosbach.ld.model.task.Task;
import com.mosbach.ld.model.task.TaskList;
import com.mosbach.ld.model.user.User;


@RestController
@RequestMapping("/api/v0.1a")
public class TaskController {

	private TaskDataManager taskManager;
	private UserDataManager userManager;
	
	@Autowired
	public TaskController(
				@Qualifier("t-postgres") TaskDataManager taskManager,
				@Qualifier("u-postgres") UserDataManager userManager
			) {
		this.taskManager = taskManager;
		this.userManager = userManager;
	}
	
	@GetMapping("/tasks")
	public ResponseEntity<?> getAllTasks() {
		UUID id = (UUID) SecurityContextHolder.getContext().getAuthentication().getDetails();
		Collection<Task> tasks = taskManager.getAllTasksOf(id);
		tasks.forEach((task) -> task.setAssignees(taskManager.getAssigneesOf(task.getId())));
		return ResponseEntity.ok(new TaskList(tasks, LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)));
	}
	
	@GetMapping("/tasks/{id}")
	public ResponseEntity<?> getTaskById(@PathVariable("id") String id) {
		if(taskManager.taskExists(UUID.fromString(id)))
			return ResponseEntity.ok(taskManager.getTaskById(UUID.fromString(id)));
		return ResponseEntity.ok(new DefaultActionResponse(false, "No such task exists..."));
	}
	
	@PostMapping(
            path = "/tasks",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
	public ResponseEntity<?> createTask(@RequestBody Task task) {
		if(task.getSwimlane() < 0 || task.getSwimlane() > 3) return ResponseEntity.ok(new DefaultActionResponse(false, "Swimlane-Index out of range...")); 
		UUID id = (UUID) SecurityContextHolder.getContext().getAuthentication().getDetails();
		boolean success = false;
		if(task.getSwimlane() != null && task.getTitle() != null)
			success = taskManager.createNewTask(task, id);
		else return ResponseEntity.ok(new DefaultActionResponse(false, "Necessary data is missing..."));
		if(success)
			return ResponseEntity.ok(new DefaultActionResponse(true, null));
		return ResponseEntity.ok(new DefaultActionResponse(false, "Error while saving data..."));
	}
	
	@PutMapping(
            path = "/tasks",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
	public ResponseEntity<?> updateTask(@RequestBody Task task) {
		boolean success = false;
		if(task.getId() != null)
			success = taskManager.updateTask(task);
		else return ResponseEntity.ok(new DefaultActionResponse(false, "Necessary values missing..."));
		if(success)
			return ResponseEntity.ok(new DefaultActionResponse(true, null));
		return ResponseEntity.ok(new DefaultActionResponse(false, "Error while saving data..."));
	}
	
	@PostMapping(
            path = "/tasks/{id}/users",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
	public ResponseEntity<?> addToTaskMembers(@PathVariable("id") String id, @RequestBody User user) {
		boolean success = false;
		UUID uid = null;
		try {
			uid = UUID.fromString(id);
		}catch(Exception e) {e.printStackTrace(); return ResponseEntity.ok(new DefaultActionResponse(false, "Task has no valid UUID..."));}
		if(user.getId() != null && userManager.userExists(user.getId()))
			success = taskManager.addToTaskMembers(uid, user);
		else return ResponseEntity.ok(new DefaultActionResponse(false, "Necessary values missing..."));
		if(success)
			return ResponseEntity.ok(new DefaultActionResponse(true, null));
		return ResponseEntity.ok(new DefaultActionResponse(false, "Error while saving data..."));
	}
	
	@GetMapping("/tasks/{id}/users")
	public ResponseEntity<?> getTaskMembers(@PathVariable("id") String id) {
		UUID uuid = null;
		try {
			uuid = UUID.fromString(id);
		}catch(Exception e) {e.printStackTrace(); return ResponseEntity.ok(new DefaultActionResponse(false, "Parameters not valid..."));}
		if(uuid != null && taskManager.taskExists(uuid))
			return ResponseEntity.ok(taskManager.getAssigneesOf(uuid));
		return ResponseEntity.ok(new DefaultActionResponse(false, "No such task exists..."));
	}
	
	@DeleteMapping(
            path = "/tasks/{id}/users/{userId}"
    )
	public ResponseEntity<?> deleteFromTaskMembers(@PathVariable("id") String id, @PathVariable("userId") String userId) {
		boolean success = false;
		UUID uuid = null, useruuId = null;
		try {
			uuid = UUID.fromString(id);
			useruuId = UUID.fromString(userId);
		}catch(Exception e) {e.printStackTrace(); return ResponseEntity.ok(new DefaultActionResponse(false, "Parameters not valid..."));}
		if(uuid != null && useruuId != null)
			success = taskManager.deleteFromTaskMembers(uuid, useruuId);
		else return ResponseEntity.ok(new DefaultActionResponse(false, "Necessary values missing..."));
		if(success)
			return ResponseEntity.ok(new DefaultActionResponse(true, null));
		return ResponseEntity.ok(new DefaultActionResponse(false, "Error while saving data..."));
	}
	
	@DeleteMapping(
            path = "/tasks/{id}"
    )
	public ResponseEntity<?> deleteTask(@PathVariable("id") String id) {
		boolean success = false;
		UUID tid = null;
		try {
			tid = UUID.fromString(id);
		}catch(Exception e) {e.printStackTrace(); return ResponseEntity.ok(new DefaultActionResponse(false, "UUID invalid..."));}
		if(taskManager.taskExists(tid))
			success = taskManager.deleteTask(tid);
		else return ResponseEntity.ok(new DefaultActionResponse(false, "Task does not exist..."));
		if(success)
			return ResponseEntity.ok(new DefaultActionResponse(true, null));
		return ResponseEntity.ok(new DefaultActionResponse(false, "Error while deleting data..."));
	}
	
	@DeleteMapping(
            path = "/tasks"
    )
	public ResponseEntity<?> deleteAllTasks() {
		UUID id = (UUID) SecurityContextHolder.getContext().getAuthentication().getDetails();
		Collection<Task> tasks = taskManager.getAllTasksOf(id);
		tasks.forEach((task) -> {
			try {
				taskManager.deleteTask(task.getId());
			}catch(Exception e) {e.printStackTrace();}
		});
		if(taskManager.getAllTasksOf(id).size() == 0)
			return ResponseEntity.ok(new DefaultActionResponse(true, null));
		return ResponseEntity.ok(new DefaultActionResponse(false, "Idk man, I guess there was an error somewhere"));
	}
	
}
