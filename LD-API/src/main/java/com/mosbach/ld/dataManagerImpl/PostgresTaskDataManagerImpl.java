package com.mosbach.ld.dataManagerImpl;

import java.util.Collection;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mosbach.ld.dataManager.TaskDataManager;
import com.mosbach.ld.model.task.Task;
import com.mosbach.ld.model.user.User;

@Repository("t-postgres")
public class PostgresTaskDataManagerImpl implements TaskDataManager {

	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	 public PostgresTaskDataManagerImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
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
		final String sql = buildQuery(task);
		try {
			jdbcTemplate.update(sql);
		}catch(Exception e) {
			return false;
		}
		return true;
	}
	private String buildQuery(Task task) {
		StringBuilder sb = new StringBuilder();
		
		
		return sb.toString();
	}

	@Override
	public boolean deleteTask(UUID id) {
		//TODO delete comments etc as well
		final String sql = 
				"DELETE FROM public.\"Task\" "
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
		return jdbcTemplate.queryForObject(sql, new Object[] {id}, (resultSet, i) -> {
			return new Task();
		});
	}

	@Override
	public Collection<User> getAssigneesOf(UUID id) {
		final String sql = "SELECT * FROM public.\"User\" WHERE id = '"+id+"' AND accepted = true; ";
		return jdbcTemplate.query(sql, (resultSet, i) -> {
			return new User();
		});
	}

	@Override
	public boolean createNewTask(Task task, UUID userId) {
		//TODO create entries in other tables too?
		final String sql = 
				"INSERT INTO public.\"Task\"\r\n"
				+ "(id, swimlane, title, description, \"due-date\", \"user-id\", created, \"created-by\", updated, \"updated-by\")\r\n"
				+ "VALUES(uuid_generate_v4(), 0, '', '', '', ?, CURRENT_DATE, ?, CURRENT_DATE, ?);";
		try {
			jdbcTemplate.update(sql);
		}catch(Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public Collection<Task> getAllTasksOf(UUID userId) {
		final String sql = "SELECT * FROM public.\"Task\" where \"user-id\"='"+userId+"';";
		return jdbcTemplate.query(sql, (resultSet, i) -> {
			return new Task(
					userId,
					i,
					"",
					sql,
					null,
					null,
					null,
					null,
					null,
					null,
					null,
					null,
					null,
					i
				);
		});
	}
	
}
