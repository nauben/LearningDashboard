package com.mosbach.ld.model.user;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

//evtl f�r sp�ter einmal
//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class User implements UserDetails {

	@JsonIgnore
	private static final long serialVersionUID = -5014614165199594545L;
	
	private List<? extends GrantedAuthority> grantedAuthorities;
	private UUID id;
	private LocalDateTime created;
	private LocalDateTime lastLogin;
	private String email;
	private String password;
	private String firstname;
	private String lastname;
	private String gender;
	private String institution;
	private String imageUrl;
	private String location;
	private Integer visibility;
	private Boolean sendNotifications;
	private Boolean isAccountNonExpired;
	private Boolean isAccountNonLocked;
	private Boolean isCredentialsNonExpired;
	private Boolean isEnabled;
	private Collection<User> contacts;
	
	public User(List<? extends GrantedAuthority> grantedAuthorities, UUID id, LocalDateTime created,
			LocalDateTime lastLogin, String email, String password, String firstname, String lastname, String gender,
			String institution, String imageUrl, String location, Integer visibility, Boolean sendNotifications,
			Boolean isAccountNonExpired, Boolean isAccountNonLocked, Boolean isCredentialsNonExpired, Boolean isEnabled,
			Collection<User> contacts) {
		super();
		this.grantedAuthorities = grantedAuthorities;
		this.id = id;
		this.created = created;
		this.lastLogin = lastLogin;
		this.email = email;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.gender = gender;
		this.institution = institution;
		this.imageUrl = imageUrl;
		this.location = location;
		this.visibility = visibility;
		this.sendNotifications = sendNotifications;
		this.setIsAccountNonExpired(isAccountNonExpired);
		this.setIsAccountNonLocked(isAccountNonLocked);
		this.setIsCredentialsNonExpired(isCredentialsNonExpired);
		this.setIsEnabled(isEnabled);
		this.contacts = contacts;
	}

	public User() {
		super();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	
	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return isAccountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return isAccountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return isCredentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return isEnabled;
	}

	public List<? extends GrantedAuthority> getGrantedAuthorities() {
		return grantedAuthorities;
	}

	public void setGrantedAuthorities(List<? extends GrantedAuthority> grantedAuthorities) {
		this.grantedAuthorities = grantedAuthorities;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	public LocalDateTime getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(LocalDateTime lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getInstitution() {
		return institution;
	}

	public void setInstitution(String institution) {
		this.institution = institution;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Integer getVisibility() {
		return visibility;
	}

	public void setVisibility(Integer visibility) {
		this.visibility = visibility;
	}

	public Boolean isSendNotifications() {
		return sendNotifications;
	}

	public void setSendNotifications(Boolean sendNotifications) {
		this.sendNotifications = sendNotifications;
	}

	public void setAccountNonExpired(Boolean isAccountNonExpired) {
		this.setIsAccountNonExpired(isAccountNonExpired);
	}

	public void setAccountNonLocked(Boolean isAccountNonLocked) {
		this.setIsAccountNonLocked(isAccountNonLocked);
	}

	public void setCredentialsNonExpired(Boolean isCredentialsNonExpired) {
		this.setIsCredentialsNonExpired(isCredentialsNonExpired);
	}

	public void setEnabled(Boolean isEnabled) {
		this.setIsEnabled(isEnabled);
	}

	public Collection<User> getContacts() {
		return contacts;
	}

	public void setContacts(Collection<User> contacts) {
		this.contacts = contacts;
	}

	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public Boolean getIsCredentialsNonExpired() {
		return isCredentialsNonExpired;
	}

	public void setIsCredentialsNonExpired(Boolean isCredentialsNonExpired) {
		this.isCredentialsNonExpired = isCredentialsNonExpired;
	}

	public Boolean getIsAccountNonLocked() {
		return isAccountNonLocked;
	}

	public void setIsAccountNonLocked(Boolean isAccountNonLocked) {
		this.isAccountNonLocked = isAccountNonLocked;
	}

	public Boolean getIsAccountNonExpired() {
		return isAccountNonExpired;
	}

	public void setIsAccountNonExpired(Boolean isAccountNonExpired) {
		this.isAccountNonExpired = isAccountNonExpired;
	}

	@Override
	public String toString() {
		return "User [grantedAuthorities=" + grantedAuthorities + ", id=" + id + ", created=" + created + ", lastLogin="
				+ lastLogin + ", email=" + email + ", password=" + password + ", firstname=" + firstname + ", lastname="
				+ lastname + ", gender=" + gender + ", institution=" + institution + ", imageUrl=" + imageUrl
				+ ", location=" + location + ", visibility=" + visibility + ", sendNotifications=" + sendNotifications
				+ ", isAccountNonExpired=" + isAccountNonExpired + ", isAccountNonLocked=" + isAccountNonLocked
				+ ", isCredentialsNonExpired=" + isCredentialsNonExpired + ", isEnabled=" + isEnabled + ", contacts="
				+ contacts + "]";
	}
	
	
	
}
