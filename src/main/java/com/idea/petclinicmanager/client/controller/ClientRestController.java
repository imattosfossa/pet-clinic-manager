package com.idea.petclinicmanager.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idea.petclinicmanager.client.dto.ClientDTO;
import com.idea.petclinicmanager.client.entity.Client;
import com.idea.petclinicmanager.client.service.IClientService;

@RestController
@RequestMapping("/clients")
public class ClientRestController {
	@Autowired
	IClientService clientService;
	
	@PostMapping
    public ResponseEntity<Client> save(@RequestBody ClientDTO clientDTO) {
        Client createdClient = clientService.insert(clientDTO);
        return new ResponseEntity<>(createdClient, HttpStatus.CREATED);
    }
	
	@PutMapping("/{clientId}")
    public ResponseEntity<Client> update(@PathVariable String clientId, @RequestBody ClientDTO clientDTO) {
		Client updatedClient = clientService.update(clientDTO, clientId);
        return new ResponseEntity<>(updatedClient, HttpStatus.ACCEPTED);
    }
	
	@GetMapping
    public ResponseEntity<Page<Client>> findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
    		@RequestParam(defaultValue = "createDate") String sortField, @RequestParam(defaultValue = "DESC") String sortType,
    		@RequestParam String document, @RequestParam String name, @RequestParam String email) {
        Page<Client> clientPage = clientService.findAll(page, size, document, name, email, sortField, sortType);
        return new ResponseEntity<>(clientPage, HttpStatus.ACCEPTED);
    }
}