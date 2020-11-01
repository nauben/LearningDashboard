package com.mosbach.ld.dataManager;

import com.mosbach.ld.model.task.Task;
import com.mosbach.ld.model.user.User;

import java.util.Collection;
import java.util.UUID;

public interface TaskDataManager {

    public boolean taskExists(UUID id);
	
	public boolean createNewTask(Task task, UUID userId);
	
	public boolean updateTask(Task task);
	
	public boolean deleteTask(UUID id);
	
	public Task getTaskById(UUID id);
	
	public Collection<User> getAssigneesOf(UUID id);
	
	public Collection<Task> getAllTasksOf(UUID userId);

}
