package com.mosbach.ld.model.mail;

import java.util.Collection;

public class EMail {

	private String subject;
	private String body;
	private Collection<String> recipients;
	
	public EMail(String subject, String body, Collection<String> recipients) {
		super();
		this.subject = subject;
		this.body = body;
		this.recipients = recipients;
	}

	public String getSubject() {
		return subject;
	}

	public String getBody() {
		return body;
	}

	public Collection<String> getRecipients() {
		return recipients;
	}
	
}
