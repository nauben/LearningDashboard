package com.mosbach.ld.dataManager;

import com.mosbach.ld.model.task.Activity;
import com.mosbach.ld.model.task.CheckItem;
import com.mosbach.ld.model.task.Comment;
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
	
	public Collection<Comment> getCommentsOf(UUID id);
	
	public Collection<Activity> getActivitiesOf(UUID id);
	
	public Collection<CheckItem> getChecklistOf(UUID id);
	
	public boolean addCommentTo(UUID tid, Comment c);
	
	public boolean deleteComment(UUID id);
	
	public boolean addActivityTo(UUID id, Activity a);
	
	public boolean addCheckItemTo(UUID id, CheckItem c);
	
	public boolean updateCheckItem(CheckItem checkItem);
	
	public boolean deleteCheckItem(UUID id);
	
	public boolean addToTaskMembers(UUID id, User user);
	
	public boolean deleteFromTaskMembers(UUID id, UUID user);

}
