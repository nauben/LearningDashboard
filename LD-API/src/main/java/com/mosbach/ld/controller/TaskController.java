package com.mosbach.ld.controller;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mosbach.ld.dataManager.TaskDataManager;
import com.mosbach.ld.model.task.Task;
import com.mosbach.ld.model.task.TaskList;

@RestController
@RequestMapping("/api/v0.1a")
public class TaskController {

	private TaskDataManager taskManager;
	
	@Autowired
	public TaskController(@Qualifier("t-postgres") TaskDataManager taskManager) {
		this.taskManager = taskManager;
	}
	
	@GetMapping("/tasks")
	public ResponseEntity<?> getAllTasks() {
		return ResponseEntity.ok(new TaskList(taskManager.getAllTasks(), LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)));
	}
	
	@GetMapping("/tasks/{id}")
	public ResponseEntity<?> getTaskById(@PathVariable("id") String id) {
		if(taskManager.taskExists(UUID.fromString(id)))
			return ResponseEntity.ok(taskManager.getTaskById(UUID.fromString(id)));
		return null;
	}
	
	@PostMapping(
            path = "/tasks",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
	public ResponseEntity<?> createTask(@RequestBody Task task) {
		if(task.getSwimlane() != null && task.getTitle() != null)
			taskManager.createNewTask(task);
		return null;
	}
	
	@PutMapping(
            path = "/tasks/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
	public ResponseEntity<?> updateTask(@PathVariable("id") String id, @RequestBody Task task) {
		if(task.getId() != null && task.getSwimlane() != null && task.getTitle() != null)
			taskManager.updateTask(task);
		return null;
	}
	
	@DeleteMapping(
            path = "/tasks/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
	public ResponseEntity<?> deleteTask(@PathVariable("id") String id) {
		
		return null;
	}
	
	@DeleteMapping(
            path = "/tasks",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
	public ResponseEntity<?> deleteAllTasks() {
		
		return null;
	}
	
}
