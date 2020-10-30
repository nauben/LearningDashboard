package com.mosbach.ld.dataManagerImpl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mosbach.ld.dataManager.DHBWScheduleDataManager;

@Repository("s-postgres")
public class DHBWScheduleDataManagerImpl implements DHBWScheduleDataManager {

	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	 public DHBWScheduleDataManagerImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public boolean setCourseOf(UUID id, String course) {
		final String sql = 
				"UPDATE public.\"Schedule-Settings\" "
				+ "SET course='"+course+"' "
				+ "WHERE \"user-id\"='"+id+"';";
		try {
			jdbcTemplate.update(sql);
		}catch(Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public String getCourseOf(UUID id) {
		final String sql = "SELECT * FROM public.\"Schedule-Settings\" WHERE \"user-id\" = ?;";
		return jdbcTemplate.queryForObject(sql, new Object[] {id}, (resultSet, i) -> {
			return resultSet.getString("course");
		});
	}

}
