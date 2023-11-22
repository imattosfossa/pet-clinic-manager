package com.idea.petclinicmanager.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idea.petclinicmanager.user.repository.IUserRepository;

@Service
public class UserService implements IUserService{

	@Autowired
    IUserRepository repository;
    
    
}
