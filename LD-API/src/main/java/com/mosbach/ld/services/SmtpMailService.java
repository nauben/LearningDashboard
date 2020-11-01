package com.mosbach.ld.services;


import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.mosbach.ld.config.SmtpMailConfig;
import com.mosbach.ld.model.EMail;

@Service
public class SmtpMailService {
	
	private SmtpMailConfig config;
	
	private Session mailSession;
	private Properties mailServerProperties;
	
	
	public SmtpMailService(SmtpMailConfig config) {
		
		this.config = config;
		
		mailServerProperties = System.getProperties();
		mailServerProperties.put("mail.smtp.port", config.getPort());
		mailServerProperties.put("mail.smtp.auth", config.isAuthentication());
		mailServerProperties.put("mail.smtp.starttls.enable", config.isStarttlsEnable());
		System.out.println("Mailserver setup finished");
		
		mailSession = Session.getDefaultInstance(mailServerProperties, null);
	}
	
	@Async
	public void sendEMail(EMail email) {
		MimeMessage message = null;
		Transport transport = null;
		try {
			message = generateMailMessage(mailSession, 
					new InternetAddress(config.getEmail(), config.getShowName()), email);
			transport = mailSession.getTransport("smtp");
			transport.connect(config.getSmtpHost(), config.getEmail(), config.getPassword());
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (UnsupportedEncodingException | MessagingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private MimeMessage generateMailMessage(Session mailSession, 
			Address from, 
			EMail mail) throws AddressException, MessagingException {
		
		MimeMessage mailMessage = new MimeMessage(mailSession);
		for(String a : mail.getRecipients()) {
			try {
			mailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(a));
			} catch(AddressException e) {}
		}
		mailMessage.setFrom(from);
		mailMessage.setSubject(mail.getSubject());
		mailMessage.setContent(mail.getBody(), "text/html");
		return mailMessage;
	}
    
}
