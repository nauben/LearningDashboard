package com.mosbach.ld.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application.mail")
public class SmtpMailConfig {

	private String smtpHost; //smtp.gmail.com
	private String showName; // Learning Dashboard
	private String email; // noreply.learningdashboard@gmail.com
	private String password; // #Webprogrammierung
	private int port; // 587
	private boolean authentication; //true
	private boolean starttlsEnable; //true
	
	public SmtpMailConfig() {
		super();
	}
	
	public String getSmtpHost() {
		return smtpHost;
	}
	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}
	public String getShowName() {
		return showName;
	}
	public void setShowName(String showName) {
		this.showName = showName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public boolean isAuthentication() {
		return authentication;
	}
	public void setAuthentication(boolean authentication) {
		this.authentication = authentication;
	}
	public boolean isStarttlsEnable() {
		return starttlsEnable;
	}
	public void setStarttlsEnable(boolean starttlsEnable) {
		this.starttlsEnable = starttlsEnable;
	}
	
}
