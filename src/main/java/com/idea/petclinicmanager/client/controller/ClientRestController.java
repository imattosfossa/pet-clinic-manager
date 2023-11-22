package com.idea.petclinicmanager.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.idea.petclinicmanager.client.dto.ClientDTO;
import com.idea.petclinicmanager.client.entity.Client;
import com.idea.petclinicmanager.client.service.IClientService;

@RestController
public class ClientRestController {
	@Autowired
	IClientService clientService;
	
	@PostMapping("/client")
    public ResponseEntity<Client> save(@RequestBody ClientDTO clientDTO) {
        Client createdClient = clientService.insert(clientDTO);
        return new ResponseEntity<>(createdClient, HttpStatus.CREATED);
    }
	
//	@PutMapping("/user/{userId}")
//    public ResponseEntity<User> update(@PathVariable Long userId, @RequestBody UserVO userVO) {
//        User updatedUser = userService.update(userVO, userId);
//        return new ResponseEntity<>(updatedUser, HttpStatus.ACCEPTED);
//    }
//	
//	@GetMapping("/user/{userId}")
//    public ResponseEntity<User> findById(@PathVariable Long userId) {
//        User user = userService.findById(userId);
//        return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
//    }
}