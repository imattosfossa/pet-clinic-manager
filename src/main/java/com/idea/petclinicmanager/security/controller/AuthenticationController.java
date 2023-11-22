package com.idea.petclinicmanager.security.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.idea.petclinicmanager.exceptions.BusinessException;
import com.idea.petclinicmanager.security.TokenService;
import com.idea.petclinicmanager.security.dto.AuthenticationDTO;
import com.idea.petclinicmanager.security.dto.LoginResponseDTO;
import com.idea.petclinicmanager.security.dto.RegisterDTO;
import com.idea.petclinicmanager.user.entity.User;
import com.idea.petclinicmanager.user.repository.IUserRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("auth")
public class AuthenticationController {
	@Autowired
    private AuthenticationManager authenticationManager;
    
	@Autowired
    private IUserRepository repository;
   
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO authentication) {
    	var usernamePassword = new UsernamePasswordAuthenticationToken(authentication.email(), authentication.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        User user = (User) auth.getPrincipal();
        if (user.getActive() == null || user.getActive() == false) {
        	throw new BusinessException("Email confirmation required.");
        }
        
        String token = tokenService.generateToken(user);

    	return new ResponseEntity<>(new LoginResponseDTO(token), HttpStatus.ACCEPTED);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterDTO register) {
    	try {
    		if (this.repository.findByEmail(register.email()) != null) throw new BusinessException("There is already an account with the " + register.email() + " login");
	        String encryptedPassword = new BCryptPasswordEncoder().encode(register.password());
	        User newUser = new User(register.email(), encryptedPassword, register.role(), false);
	
	        this.repository.save(newUser);
	
	        String token = tokenService.generateToken(newUser);

	    	return new ResponseEntity<>(new LoginResponseDTO(token), HttpStatus.CREATED);
    	} catch (Exception ex) {
    		throw new BusinessException(ex.getMessage());
    	}
    }
    
    @PutMapping("/active/{id}")
    public ResponseEntity<?> active(@PathVariable String id) {
    	Optional<User> userOptional = repository.findById(id);
        
        if (!userOptional.isPresent()) {
        	throw new BusinessException("User not found");
        }
        
        User user = userOptional.get();
    	user.setActive(true);
    	repository.save(user);
    	
    	return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
