package com.mosbach.ld.services;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserCredentialService implements UserDetailsService{

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//TODO get User from Postgre database
		//TODO may override user class
		return new User("foo", "foo", new ArrayList<>());
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		//TODO password encoder
		return NoOpPasswordEncoder.getInstance();
	}

}
