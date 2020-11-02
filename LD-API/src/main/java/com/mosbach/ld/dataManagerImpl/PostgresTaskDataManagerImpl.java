package com.mosbach.ld.dataManagerImpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
					LocalDateTime.parse(resultSet.getString("due-date"), formatter),
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
		final String sql = "SELECT c.id \"cid\", c.\"task-id\", c.\"user-id\", c.message, c.\"Timestamp\", u.id \"uid\", u.email, u.firstname, u.lastname "
				+ "FROM public.\"Comments\" c, public.\"User\" u "
				+ "where c.\"task-id\" = '"+id+"' and c.\"user-id\" = u.id "
				+ "order by \"Timestamp\" desc;";
		System.out.println(sql);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return jdbcTemplate.query(sql, (resultSet, i) -> {
			return new Comment(
					UUID.fromString(resultSet.getString("cid")),
					LocalDateTime.parse(resultSet.getString("Timestamp"), formatter),
					resultSet.getString(""),
					new User(
							null,
							UUID.fromString(resultSet.getString("uid")),
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
							null,
							null,
							null,
							null,
							null,
							null,
							null
						)
				);
		});
	}

	@Override
	public Collection<Activity> getActivitiesOf(UUID id) {
		final String sql = "SELECT a.id \"aid\", a.\"user-id\", a.\"timestamp\", a.activity, a.\"task-id\", u.id \"uid\", u.email, u.firstname, u.lastname \r\n"
				+ "FROM public.\"Activities\" a, public.\"User\" u \r\n"
				+ "where \"task-id\"='"+id+"' and a.\"user-id\" = u.id \r\n"
				+ "order by \"timestamp\" desc;";
		System.out.println(sql);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return jdbcTemplate.query(sql, (resultSet, i) -> {
			return new Activity(
					UUID.fromString(resultSet.getString("aid")), 
					new User(
							null,
							UUID.fromString(resultSet.getString("uid")),
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
							null,
							null,
							null,
							null,
							null,
							null,
							null
						), 
					LocalDateTime.parse(resultSet.getString("Timestamp"), formatter), 
					resultSet.getString("description")
				);
		});
	}

	@Override
	public Collection<CheckItem> getChecklistOf(UUID id) {
		final String sql = "SELECT id, \"task-id\", checked, description "
				+ "FROM public.\"Checklist\" "
				+ "where \"task-id\"='"+id+"' "
				+ "order by checked;";
		System.out.println(sql);
		return jdbcTemplate.query(sql, (resultSet, i) -> {
			return new CheckItem(
					UUID.fromString(resultSet.getString("id")),
					resultSet.getString("description"),
					false
				);
		});
	}

	@Override
	public boolean addCommentTo(UUID tid, Comment c) {
		String desc = c.getMessage().replace("'", "''");
		final String sql = 
				"INSERT INTO public.\"Comments\" "
				+ "(id, \"task-id\", \"user-id\", message, \"Timestamp\") "
				+ "VALUES(uuid_generate_v4(), '"+tid+"', '"+c.getUser().getId()+"', '"+desc+"', date_trunc('second'::text, CURRENT_TIMESTAMP));";
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
	public boolean deleteComment(UUID id) {
		final String sql = 
				"DELETE FROM public.\"Comments\" "
				+ "WHERE id='"+id+"';";
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
	public boolean addActivityTo(UUID id, Activity a) {
		final String sql = 
				"DELETE FROM public.\"Comments\" "
				+ "WHERE id='"+id+"';";
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
	public boolean addCheckItemTo(UUID id, CheckItem c) {
		final String sql = 
				"INSERT INTO public.\"Checklist\"\r\n"
				+ "(id, \"task-id\", checked, description)\r\n"
				+ "VALUES(uuid_generate_v4(), '"+id+"', false, '"+c.getDescription().replace("'", "''")+"');";
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
	public boolean updateCheckItem(CheckItem checkItem) {
		final String sql = buildQueryUpdateCheckItem(checkItem);
		System.out.println(sql);
		try {
			jdbcTemplate.update(sql);
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	private String buildQueryUpdateCheckItem(CheckItem checkItem) {
		StringBuilder sb = new StringBuilder();
		int changes = 0;
		sb.append("UPDATE public.\"Checklist\" SET ");
		if(checkItem.getDescription() != null) {
			sb.append("description='"+checkItem.getDescription().replace("'", "''")+"'");
			changes++;
		}
		if(checkItem.isChecked() != null) {
			if(changes > 0) sb.append(", ");
			sb.append("checked="+checkItem.isChecked()+" ");
			changes++;
		}
		
		sb.append(" WHERE id='"+checkItem.getId()+"';");
		if(changes > 0)
			return sb.toString();
		else return "NULL;";
	}

	@Override
	public boolean deleteCheckItem(UUID id) {
		final String sql = 
				"DELETE FROM public.\"Checklist\" "
				+ "WHERE id='"+id+"';";
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
	public boolean addToTaskMembers(UUID id, User user) {
		final String sql = 
				"INSERT INTO public.\"TaskUser\" "
				+ "(id, \"task-id\", \"user-id\") "
				+ "VALUES(uuid_generate_v4(), '"+id+"', '"+user.getId()+"');";
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
	public boolean deleteFromTaskMembers(UUID id, UUID user) {
		final String sql = 
				"DELETE FROM public.\"TaskUser\" "
				+ "WHERE \"user-id\" = '"+id+"' and \"task-id\" ='"+user+"';";
		System.out.println(sql);
		try {
			jdbcTemplate.update(sql);
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
}
