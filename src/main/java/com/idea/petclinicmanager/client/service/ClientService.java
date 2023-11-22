package com.idea.petclinicmanager.client.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idea.petclinicmanager.client.dto.ClientDTO;
import com.idea.petclinicmanager.client.entity.Client;
import com.idea.petclinicmanager.client.repository.IClientRepository;

@Service
public class ClientService implements IClientService {

    @Autowired
    IClientRepository repository;
    
    @Autowired
	private ModelMapper modelMapper;
    
    public Client insert(ClientDTO clientDTO) {
    	Client client = modelMapper.map(clientDTO, Client.class);
    	
    	repository.save(client);
    	
    	return client;
    }
    
    public ClientDTO update(ClientDTO clientDTO, String clientId) {
    	
    	return null;
    }
    
    public ClientDTO findOneById(String clientId) {
    	
    	return null;
    }
    
    public ClientDTO findAll(String userId) {
    	
    	return null;
    }
    
}
