package com.mosbach.ld.dataManagerImpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mosbach.ld.dataManager.LearnStatisticsDataManager;
import com.mosbach.ld.model.learnStatistics.Subject;

@Repository("ls-postgres")
public class LearnStatisticsDataManagerImpl implements LearnStatisticsDataManager {

	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	 public LearnStatisticsDataManagerImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public boolean createNewSubject(Subject subject, UUID userId) {
		final String sql = 
				"INSERT INTO public.\"Learning-Statistics\" "
				+ "(id, \"user-id\", subject, \"time\", \"Since\") "
				+ "VALUES(uuid_generate_v4(), '"+userId+"', '"+subject.getTitle()+"', 0, date_trunc('second', CURRENT_TIMESTAMP));";
		try {
			jdbcTemplate.update(sql);
		}catch(Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public Collection<Subject> getAllSubjectsOf(UUID userId) {
		final String sql = "SELECT * FROM public.\"Learning-Statistics\" where \"user-id\"= '"+userId+"';";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return jdbcTemplate.query(sql, (resultSet, i) -> {
			return new Subject(
					UUID.fromString(resultSet.getString("id")),
					resultSet.getString("subject"),
					resultSet.getLong("time"),
					LocalDateTime.parse(resultSet.getString("timestamp"), formatter)
				);
		});
	}

	@Override
	public boolean deleteSubject(UUID id) {
		final String sql = 
				"DELETE FROM public.\"Learning-Statistics\" WHERE id='"+id+"';";
		try {
			jdbcTemplate.update(sql);
		}catch(Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean incrementSubjectTime(UUID id, int seconds) {
		final String sql = 
				"UPDATE public.\"Learning-Statistics\""
				+ "SET \"time\"= "+seconds+"+(select \"time\" from public.\"Learning-Statistics\" where id='"+id+"')"
				+ "WHERE id='"+id+"';";
		try {
			jdbcTemplate.update(sql);
		}catch(Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean resetSubjectTime(UUID id) {
		final String sql = 
				"UPDATE public.\"Learning-Statistics\"\r\n"
				+ "SET \"time\"=0, \"Since\"=date_trunc('second', CURRENT_TIMESTAMP)\r\n"
				+ "WHERE id='"+id+"';";
		try {
			jdbcTemplate.update(sql);
		}catch(Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public Subject getSubjectById(UUID id) {
		final String sql = "SELECT * FROM public.\"Learning-Statistics\" where id= ?;";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return jdbcTemplate.queryForObject(sql, new Object[] {id}, (resultSet, i) -> {
			return new Subject(
					UUID.fromString(resultSet.getString("id")),
					resultSet.getString("subject"),
					resultSet.getLong("time"),
					LocalDateTime.parse(resultSet.getString("timestamp"), formatter)
				);
		});
	}

	@Override
	public boolean updateSubjectName(Subject subject) {
		final String sql = 
				"UPDATE public.\"Learning-Statistics\""
				+ "SET subject='"+subject.getTitle()+"'"
				+ "WHERE id='"+subject.getSubject()+"';";
		try {
			jdbcTemplate.update(sql);
		}catch(Exception e) {
			return false;
		}
		return true;
	}



}
