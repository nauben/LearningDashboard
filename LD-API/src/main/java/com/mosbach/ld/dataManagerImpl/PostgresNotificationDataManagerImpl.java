package com.mosbach.ld.dataManagerImpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mosbach.ld.dataManager.NotificationDataManager;
import com.mosbach.ld.model.notification.Notification;

@Repository("n-postgres")
public class PostgresNotificationDataManagerImpl implements NotificationDataManager {

	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	 public PostgresNotificationDataManagerImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public boolean createNewNotificationFor(UUID user, Notification notification) {
		final String sql = 
				"INSERT INTO public.\"Messages\"\r\n"
				+ "(id, \"user-id\", \"timestamp\", message, link)\r\n"
				+ "VALUES(uuid_generate_v4(), '"+user+"', CURRENT_DATE, '"+notification.getMessage()+"', '"+notification.getLink()+"');";
		try {
			jdbcTemplate.update(sql);
		}catch(Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public Collection<Notification> getAllNotificationsOf(UUID id) {
		final String sql = "SELECT * FROM public.\"Messages\" where \"user-id\"='"+id+"' ;";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return jdbcTemplate.query(sql, (resultSet, i) -> {
			return new Notification(
					UUID.fromString(resultSet.getString("id")),
					LocalDateTime.parse(resultSet.getString("timestamp"), formatter),
					resultSet.getString("message"),
					resultSet.getString("link")
				);
		});
	}

	@Override
	public boolean deleteNotification(UUID id) {
		final String sql = 
				"DELETE FROM public.\"Messages\""
				+ "WHERE id='"+id+"';";
		try {
			jdbcTemplate.update(sql);
		}catch(Exception e) {
			return false;
		}
		return true;
	}
	
}
