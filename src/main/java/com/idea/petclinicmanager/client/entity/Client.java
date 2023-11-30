package com.idea.petclinicmanager.client.entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "client")
@Entity(name = "client")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
public class Client implements Serializable {
	
    private static final long serialVersionUID = 1L;
   
    @Column(name="user_id")
	private String userId;
    
	@Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
	
    private String name;
    private String email;
    private String document;
    private Date createDate;
}
