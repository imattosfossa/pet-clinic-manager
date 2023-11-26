package com.idea.petclinicmanager.email.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.idea.petclinicmanager.email.dto.Email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService implements IEmailService {
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Override
	public void sendEmail(Email email) {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
			mimeMessageHelper.setSubject(email.getEmailSubject());
			mimeMessageHelper.setFrom(new InternetAddress(email.getEmailFrom()));
			mimeMessageHelper.setTo(email.getEmailTo());
			mimeMessageHelper.setText(email.getEmailContent(), true);
			javaMailSender.send(mimeMessageHelper.getMimeMessage());
		} 
		catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
