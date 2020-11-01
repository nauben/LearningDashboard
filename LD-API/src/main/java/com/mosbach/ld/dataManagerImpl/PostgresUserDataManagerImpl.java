package com.mosbach.ld.dataManagerImpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.mosbach.ld.dataManager.UserDataManager;
import com.mosbach.ld.model.user.User;

@Repository("u-postgres")
public class PostgresUserDataManagerImpl implements UserDataManager {

	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	 public PostgresUserDataManagerImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public boolean registerNewUser(User user) {
		//TODO proplematic what data and null?
		final String sql = 
				"INSERT INTO public.\"User\""
				+ "(id, email, \"password\", firstname, lastname, institution, \"location\", \"picture-url\", \"last-Login\", created, activated, \"blocked\")"
				+ "VALUES(uuid_generate_v4(), '"+user.getEmail()+"', '"+user.getPassword()+"', '"+user.getFirstname()+"',"
				+ " '"+user.getLastname()+"', '"+user.getInstitution()+"',"
				+ " '"+user.getLocation()+"', '', CURRENT_DATE, CURRENT_DATE, true, false); "
				+ "INSERT INTO public.\"Settings\" "
				+ "(\"user-id\", visibility, \"email-notification\") "
				+ "VALUES((select id from public.\"User\" where email='"+user.getEmail()+"'), 0, false); "
				+ "INSERT INTO public.\"Schedule-Settings\" "
				+ "(\"user-id\", course) "
				+ "VALUES((select id from public.\"User\" where email='"+user.getEmail()+"'), ''); ";
		try {
			jdbcTemplate.update(sql);
		}catch(Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean updateUser(User user) {
		final String sql = buildQuery(user);
		try {
			jdbcTemplate.update(sql);
		}catch(Exception e) {
			return false;
		}
		return true;
	}
	private String buildQuery(User user) {
		StringBuilder sb = new StringBuilder();
		int changes = 0;
		sb.append("UPDATE public.\"User\" SET ");
		if(user.getEmail() != null) {
			sb.append("email='"+user.getEmail()+"'");
			changes++;
		}
		if(user.getPassword() != null) {
			if(changes > 0) sb.append(", ");
			sb.append("\"password\"='"+user.getEmail()+"'");
			changes++;
		}
		if(user.getFirstname() != null) {
			if(changes > 0) sb.append(", ");
			sb.append("firstname='"+user.getFirstname()+"'");
			changes++;
		}
		if(user.getLastname() != null) {
			if(changes > 0) sb.append(", ");
			sb.append("lastname='"+user.getLastname()+"'");
			changes++;
		}
		if(user.getInstitution() != null) {
			if(changes > 0) sb.append(", ");
			sb.append("institution='"+user.getInstitution()+"'");
			changes++;
		}
		if(user.getLocation() != null) {
			if(changes > 0) sb.append(", ");
			sb.append("\"location\"='"+user.getLocation()+"'");
			changes++;
		}
		sb.append(" WHERE id='"+user.getId()+"';");
		if(user.getVisibility() != null || user.isSendNotifications() != null) {
			int sChanges = 0;
			sb.append(" UPDATE public.\"Settings\" SET ");
			if(user.getVisibility() != null) {
				sb.append("visibility="+user.getVisibility()+"");
				sChanges++;
			}
			if(user.isSendNotifications() != null) {
				if(sChanges > 0) sb.append(", ");
				sb.append("\"email-notification\"="+user.isSendNotifications()+"");
				sChanges++;
			}
			sb.append(" WHERE \"user-id\"='"+user.getId()+"';");
		}
		return sb.toString();
	}

	@Override
	public boolean deleteUser(UUID id) {
		//TODO delete entries in all tables
		final String sql = 
				"DELETE FROM public.\"Settings\" WHERE \"user-id\"='"+id+"';"
				+ "DELETE FROM public.\"Schedule-Settings\" WHERE \"user-id\"='"+id+"';"
				+"DELETE FROM public.\"User\" WHERE id='"+id+"';";
		try {
			jdbcTemplate.update(sql);
		}catch(Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public User getUserById(UUID id) {
		final String sql = "SELECT * FROM public.\"User\" WHERE id = ?;";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return jdbcTemplate.queryForObject(sql, new Object[] {id}, (resultSet, i) -> {
			return new User(
					new ArrayList<>(),
					UUID.fromString(resultSet.getString("id")),
					LocalDateTime.parse(resultSet.getString("created"), formatter),
					LocalDateTime.parse(resultSet.getString("last-login"), formatter),
					resultSet.getString("email"),
					resultSet.getString("password"),
					resultSet.getString("firstname"),
					resultSet.getString("lastname"),
					"",
					resultSet.getString("institution"),
					"",
					resultSet.getString("location"),
					0,
					//resultSet.getBoolean("sendNotifications"),
					false,
					false,
					!resultSet.getBoolean("blocked"),
					false,
					resultSet.getBoolean("activated"),
					null
				);
		});
	}

	@Override
	public Collection<User> getContactsOf(UUID id) {
		final String sql = "select u.id, u.email, u.\"password\", u.firstname, u.lastname, u.institution, u.\"location\", u.\"picture-url\", u.\"last-Login\", u.created, u.activated, u.\"blocked\" "
				+ "from \"User\" u, \"Contacts\" c where c.accepted = true and ((c.\"contact-id\" = "+id+" and c.\"user-id\"= u.id ) or (c.\"user-id\" = "+id+" and c.\"contact-id\" = u.id));";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return jdbcTemplate.query(sql, (resultSet, i) -> {
			return new User(
					new ArrayList<>(),
					UUID.fromString(resultSet.getString("id")),
					LocalDateTime.parse(resultSet.getString("created"), formatter),
					LocalDateTime.parse(resultSet.getString("last-login"), formatter),
					resultSet.getString("email"),
					null,
					resultSet.getString("firstname"),
					resultSet.getString("lastname"),
					"",
					resultSet.getString("institution"),
					"",
					resultSet.getString("location"),
					0,
					null,
					false,
					null,
					false,
					null,
					null
				);
		});
	}

	@Override
	public Collection<User> getAllUsers() {
		final String sql = "SELECT * FROM public.\"User\";";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return jdbcTemplate.query(sql, (resultSet, i) -> {
			return new User(
					new ArrayList<>(),
					UUID.fromString(resultSet.getString("id")),
					LocalDateTime.parse(resultSet.getString("created"), formatter),
					LocalDateTime.parse(resultSet.getString("last-login"), formatter),
					resultSet.getString("email"),
					resultSet.getString("password"),
					resultSet.getString("firstname"),
					resultSet.getString("lastname"),
					"",
					resultSet.getString("institution"),
					"",
					resultSet.getString("location"),
					0,
					resultSet.getBoolean("sendNotifications"),
					false,
					!resultSet.getBoolean("blocked"),
					false,
					resultSet.getBoolean("activated"),
					null
				);
		});
	}

	@Override
	public boolean isEmailUsed(String email) {
		final String sql = "SELECT id FROM public.\"User\" WHERE email = ?;";
		try {
			jdbcTemplate.queryForObject(sql, new Object[] {email}, (resultSet, i) -> {
				return i;
			});
		}catch(Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		final String sql = "SELECT * FROM public.\"User\" WHERE email = ?;";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return jdbcTemplate.queryForObject(sql, new Object[] {username}, (resultSet, i) -> {
			return new User(
					new ArrayList<>(),
					UUID.fromString(resultSet.getString("id")),
					LocalDateTime.parse(resultSet.getString("created"), formatter),
					LocalDateTime.parse(resultSet.getString("last-login"), formatter),
					resultSet.getString("email"),
					resultSet.getString("password"),
					resultSet.getString("firstname"),
					resultSet.getString("lastname"),
					"",
					resultSet.getString("institution"),
					"",
					resultSet.getString("location"),
					0,
					resultSet.getBoolean("sendNotifications"),
					false,
					!resultSet.getBoolean("blocked"),
					false,
					resultSet.getBoolean("activated"),
					null
				);
		});

	}

	@Override
	public boolean userExists(UUID id) {
		final String sql = "SELECT * FROM public.\"User\" WHERE id = ?; ";
		try {
			return jdbcTemplate.queryForObject(sql, new Object[] {id}, (resultSet, i) -> {
				return true;
			});
		}catch(Exception e) {
			return false;
		}
		
	}

	@Override
	public UUID getUUIDByEmail(String email) {
		final String sql = "SELECT id FROM public.\"User\" WHERE email = ?;";
		return jdbcTemplate.queryForObject(sql, new Object[] {email}, (resultSet, i) -> {
			return UUID.fromString(resultSet.getString("id"));
		});
	}

	@Override
	public boolean setNewContactOf(UUID id, UUID contact) {
		final String sql = 
				"INSERT INTO public.\"Contacts\" (\"user-id\", \"contact-id\", accepted)"
				+ "VALUES('"+id+"', '"+contact+"', true);";
		try {
			jdbcTemplate.update(sql);
		}catch(Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean updateUserLogin(UUID user) {
		final String sql = 
				"update \"User\" set (\"last-Login\") as (current_time) where id = '"+user+"';";
		try {
			jdbcTemplate.update(sql);
		}catch(Exception e) {
			return false;
		}
		return true;
	}

}
