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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.idea.petclinicmanager.email.dto.Email;
import com.idea.petclinicmanager.email.service.IEmailService;
import com.idea.petclinicmanager.exceptions.BusinessException;
import com.idea.petclinicmanager.security.TokenService;
import com.idea.petclinicmanager.security.dto.AuthenticationDTO;
import com.idea.petclinicmanager.security.dto.LoginResponseDTO;
import com.idea.petclinicmanager.security.dto.RegisterDTO;
import com.idea.petclinicmanager.user.UserThreadLocal;
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
    
    @Autowired
	private IEmailService emailService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO authentication) {
    	var usernamePassword = new UsernamePasswordAuthenticationToken(authentication.email(), authentication.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        
        User user = (User) auth.getPrincipal();
        if (user.getActive() == null || user.getActive() == false) {
        	throw new BusinessException("Email confirmation required.");
        }
        
        String token = tokenService.generateToken(user);

        UserThreadLocal.set(user);
    	return new ResponseEntity<>(new LoginResponseDTO(token), HttpStatus.ACCEPTED);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterDTO register) {
    	try {
    		if (this.repository.findByEmail(register.email()) != null) throw new BusinessException("There is already an account with the " + register.email() + " login");
	        String encryptedPassword = new BCryptPasswordEncoder().encode(register.password());
	        User newUser = new User(register.email(), encryptedPassword, register.role(), false, false, register.dateOfBirth(), register.document(), register.name());
	
	        this.repository.save(newUser);
	
	        String token = tokenService.generateToken(newUser);

	        Email email = new Email();
	        email.setEmailFrom(newUser.getEmail());
	        email.setEmailTo(newUser.getEmail());
	        email.setEmailSubject("Confirmação de e-mail Pet Clinic Manager");
	        email.setEmailContent("<!DOCTYPE html>\n"
	                + "<html lang=\"en\">\n"
	                + "<head>\n"
	                + "    <meta charset=\"UTF-8\">\n"
	                + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
	                + "    <title>Confirmação de Senha</title>\n"
	                + "</head>\n"
	                + "<body>\n"
	                + "    <h2>Confirmação de Senha</h2>\n"
	                + "    <p>Obrigado por confirmar sua senha! Clique no botão abaixo para finalizar o processo:</p>\n"
	                + "\n"
	                + "    <!-- Formulário de Confirmação -->\n"
	                + "    <form method=\"post\" action=\"http://localhost:8080/auth/confirmed/" + newUser.getId() + "\">\n"
	                + "        <button type=\"submit\" style=\"padding: 10px; background-color: #4CAF50; color: white; border: none; border-radius: 5px; cursor: pointer;\">Confirmar Senha</button>\n"
	                + "    </form>\n"
	                + "\n"
	                + "    <p>Obrigado!</p>\n"
	                + "</body>\n"
	                + "</html>\n"
	                + "");
	        emailService.sendEmail(email);

	    	return new ResponseEntity<>(new LoginResponseDTO(token), HttpStatus.CREATED);
    	} catch (Exception ex) {
    		throw new BusinessException(ex.getMessage());
    	}
    }
    
    @PostMapping("/confirmed/{id}")
    public ResponseEntity<?> confirmed(@PathVariable String id) {
    	Optional<User> userOptional = repository.findById(id);
        
        if (userOptional.isEmpty()) {
        	throw new BusinessException("User not found");
        }
        
        User user = userOptional.get();
    	user.setActive(true);
    	user.setConfirmed(true);
    	repository.save(user);
    	
    	return new ResponseEntity<>("Cadastro confirmado com sucesso!", HttpStatus.ACCEPTED);
    }
}
