package com.idea.petclinicmanager.email.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@NoArgsConstructor
public class Email {
	private String emailFrom;
    private String emailTo;
    private String emailCc;
    private String emailBcc;
    private String emailSubject;
    private String emailContent;
    private String contentType = "text/html; charset=utf-8";
    private List <Object> attachments;
    
    public Date getMailSendDate() {
        return new Date();
    }
}
