package com.mosbach.ld.dataManager;

import java.util.Collection;
import java.util.UUID;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.mosbach.ld.model.user.User;

public interface UserDataManager extends UserDetailsService {

	public boolean userExists(UUID user);
	
	public boolean isEmailUsed(String email);
	
	public UUID getUUIDByEmail(String email);
	
	public boolean registerNewUser(User user);
	
	public boolean updateUser(User user);
	
	public boolean updateUserLogin(UUID user);
	
	public boolean deleteUser(UUID id);
	
	public User getUserById(UUID id);
	
	public boolean setNewContactOf(UUID id, UUID contact);
	
	public Collection<User> getContactsOf(UUID id);
	
	public Collection<User> getAllUsers();
	
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
