package com.mosbach.ld.services;

import java.util.Collection;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mosbach.ld.dataManager.UserDataManager;
import com.mosbach.ld.model.user.User;

@Service
public class UserService implements UserDetailsService {

	private final UserDataManager dataManager;
	
	@Autowired
	public UserService(@Qualifier("u-postgres") UserDataManager dataManager) {
		this.dataManager = dataManager;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if(!isEmailUsed(username)) 
			throw new UsernameNotFoundException(username);
		return dataManager.loadUserByUsername(username);
		
	}
	
	public boolean userExists(UUID id) {
		return dataManager.userExists(id);
	}
	
	public boolean isEmailUsed(String email) {
		return dataManager.isEmailUsed(email);
	}
	
	public UUID getUUIDByEmail(String email) {
		return dataManager.getUUIDByEmail(email);
	}
	
	public boolean registerNewUser(User user) {
		if(!isEmailUsed(user.getUsername()))
			return dataManager.registerNewUser(user);
		else return false;
	}
	
	public boolean updateUser(User user) {
		if(userExists(user.getId()))
			return dataManager.updateUser(user);
		return false;
	}
	
	public boolean deleteUser(UUID id) {
		if(userExists(id))
			return dataManager.deleteUser(id);
		return false;
	}
	
	public User getUserById(UUID id) {
		return dataManager.getUserById(id);
	}
	
	public Collection<User> getContactsof(UUID id){
		if(userExists(id))
			return dataManager.getContactsOf(id);
		return null;
	}
	
	public Collection<User> getAllUsers() {
		return dataManager.getAllUsers();
	}
	
	
}
