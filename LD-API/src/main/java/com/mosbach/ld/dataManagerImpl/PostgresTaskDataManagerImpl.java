package com.mosbach.ld.dataManagerImpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import com.mosbach.ld.dataManager.TaskDataManager;
import com.mosbach.ld.dataManager.UserDataManager;
import com.mosbach.ld.model.task.Activity;
import com.mosbach.ld.model.task.CheckItem;
import com.mosbach.ld.model.task.Comment;
import com.mosbach.ld.model.task.Task;
import com.mosbach.ld.model.user.User;

@Repository("t-postgres")
public class PostgresTaskDataManagerImpl implements TaskDataManager {

	private final JdbcTemplate jdbcTemplate;
	private UserDataManager userManager;
	
	@Autowired
	 public PostgresTaskDataManagerImpl(JdbcTemplate jdbcTemplate,
			 UserDataManager userManager) {
		this.jdbcTemplate = jdbcTemplate;
		this.userManager = userManager;
	}

	@Override
	public boolean taskExists(UUID id) {
		final String sql = "SELECT id FROM public.\"Task\" WHERE id = ?;";
		try {
			jdbcTemplate.queryForObject(sql, new Object[] {id}, (resultSet, i) -> {
				return i;
			});
		}catch(Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean updateTask(Task task) {
		UUID id = (UUID) SecurityContextHolder.getContext().getAuthentication().getDetails();
		final String sql = buildQuery(task, id);
		System.out.println(sql);
		try {
			jdbcTemplate.update(sql);
		}catch(Exception e) {
			return false;
		}
		return true;
	}
	private String buildQuery(Task task, UUID userId) {
		StringBuilder sb = new StringBuilder();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		int changes = 0;
		sb.append("UPDATE public.\"Task\" SET ");
		if(task.getSwimlane() != null) {
			sb.append("swimlane ="+task.getSwimlane()+"");
			changes++;
		}
		if(task.getTitle() != null) {
			if(changes > 0) sb.append(", ");
			sb.append("title ='"+task.getTitle()+"'");
			changes++;
		}
		if(task.getDescription() != null) {
			if(changes > 0) sb.append(", ");
			sb.append("description ='"+task.getDescription()+"'");
			changes++;
		}
		if(task.getLabel() != null) {
			if(changes > 0) sb.append(", ");
			sb.append("\"label\" = "+task.getTitle()+"");
			changes++;
		}
		if(task.getDueDate() != null) {
			if(changes > 0) sb.append(", ");
			sb.append("\"due-date\" ='"+task.getDueDate().format(formatter)+"'");
			changes++;
		}
		if(changes > 0) {
			sb.append(", updated=date_trunc('second'::text, CURRENT_TIMESTAMP), \"updated-by\"='"+userId+"'");
		}
		sb.append(" WHERE id='"+task.getId()+"';");
		if(changes > 0)
			return sb.toString();
		else return "NULL;";
	}

	@Override
	public boolean deleteTask(UUID id) {
		final String sql = 
				"DELETE FROM public.\"Activities\" "
				+ "WHERE \"task-id\"='"+id+"'; "
				+ "DELETE FROM public.\"Checklist\" "
				+ "WHERE \"task-id\"='"+id+"'; "
				+ "DELETE FROM public.\"Comments\" "
				+ "WHERE \"task-id\"='"+id+"'; "
				+ "DELETE FROM public.\"TaskUser\" "
				+ "WHERE \"task-id\"='"+id+"'; "
				+ "DELETE FROM public.\"Task\" "
				+ "WHERE id='"+id+"';";
		try {
			jdbcTemplate.update(sql);
		}catch(Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public Task getTaskById(UUID id) {
		final String sql = "SELECT * "
				+ "FROM public.\"Task\" where id='"+id+"';";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return jdbcTemplate.queryForObject(sql, new Object[] {id}, (resultSet, i) -> {
			return new Task(
					UUID.fromString(resultSet.getString("id")),
					resultSet.getInt("swimlane"),
					resultSet.getString("title"),
					resultSet.getString("description"),
					LocalDateTime.parse(resultSet.getString("due-atde"), formatter),
					null,
					LocalDateTime.parse(resultSet.getString("created"), formatter),
					null,
					LocalDateTime.parse(resultSet.getString("updated"), formatter),
					null,
					null,
					null,
					null,
					resultSet.getInt("label")
				);
		});
	}

	@Override
	public Collection<User> getAssigneesOf(UUID id) {
		final String sql = 
				"SELECT u.id, u.email, u.\"password\", u.firstname, u.lastname, u.institution, u.\"location\", u.\"picture-url\", u.\"last-Login\", u.created, u.activated, u.\"blocked\" "
				+ "FROM public.\"User\" u, public.\"TaskUser\" tu where tu.\"user-id\" = u.id and tu.\"task-id\" = '"+id+"';";
		return jdbcTemplate.query(sql, (resultSet, i) -> {
			return new User(
					null,
					UUID.fromString(resultSet.getString("id")),
					null,
					null,
					resultSet.getString("email"),
					null,
					resultSet.getString("firstname"),
					resultSet.getString("lastname"),
					null,
					null,
					null,
					null,
					0,
					null,
					null,
					null,
					null,
					null,
					null
				);
		});
	}

	@Override
	public boolean createNewTask(Task task, UUID userId) {
		UUID taskId = UUID.randomUUID();
		final String sql = 
				"INSERT INTO public.\"Task\" "
				+ "(id, swimlane, title, description, \"label\",  \"due-date\", created, \"created-by\", updated, \"updated-by\") "
				+ "VALUES('"+taskId+"', "+task.getSwimlane()+", '"+task.getTitle()+"', '',0, NULL, date_trunc('second', CURRENT_TIMESTAMP), '"+userId+"', date_trunc('second', CURRENT_TIMESTAMP), '"+userId+"'); "
				+ "INSERT INTO public.\"TaskUser\" "
				+ "(id, \"task-id\", \"user-id\") "
				+ "VALUES(uuid_generate_v4(), '"+taskId+"', '"+userId+"');"
				+ "INSERT INTO public.\"Activities\" "
				+ "(id, \"user-id\", \"timestamp\", activity, \"task-id\") "
				+ "VALUES(uuid_generate_v4(), '"+userId+"', date_trunc('second', CURRENT_TIMESTAMP), 100, '"+taskId+"');";
		System.out.println(sql);
		try {
			jdbcTemplate.update(sql);
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public Collection<Task> getAllTasksOf(UUID userId) {
		final String sql = "SELECT t.id, t.swimlane, t.title, t.description, t.\"label\", t.\"due-date\", t.created, t.\"created-by\", t.updated, t.\"updated-by\" "
				+ "FROM public.\"Task\" t, public.\"TaskUser\" tu where t.id = tu.\"task-id\" and tu.\"user-id\" = '"+userId+"';";
		System.out.println(sql);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return jdbcTemplate.query(sql, (resultSet, i) -> {
			LocalDateTime dueDate = null;
			if(resultSet.getString("due-date") == null)
				dueDate = null;
			else
				dueDate = LocalDateTime.parse(resultSet.getString("due-date"), formatter);
			return new Task(
					UUID.fromString(resultSet.getString("id")),
					resultSet.getInt("swimlane"),
					resultSet.getString("title"),
					resultSet.getString("description"),
					dueDate,
					null,
					LocalDateTime.parse(resultSet.getString("created"), formatter),
					null,
					LocalDateTime.parse(resultSet.getString("updated"), formatter),
					null,
					null,
					null,
					null,
					resultSet.getInt("label")
				);
		});
	}

	@Override
	public Collection<Comment> getCommentsOf(UUID id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Activity> getActivitiesOf(UUID id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<CheckItem> getChecklistOf(UUID id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addCommentTo(UUID id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteComment(UUID id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addActivityTo(UUID id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addCheckItemTo(UUID id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateCheckItem(CheckItem checkItem) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteCheckItem(UUID id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addToTaskMembers(UUID id, User user) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
