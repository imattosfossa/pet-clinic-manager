package com.idea.petclinicmanager.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class Exception extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public Exception(String ex) {
		super(ex);
	}
}
