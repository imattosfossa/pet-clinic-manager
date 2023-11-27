package com.idea.petclinicmanager.client.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ClientDTO implements Serializable {
	
    private static final long serialVersionUID = 1L;
   
    private String name;
    private String email;
    private String document;
}
