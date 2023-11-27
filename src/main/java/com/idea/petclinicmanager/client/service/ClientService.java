package com.idea.petclinicmanager.client.service;

import org.springframework.data.domain.Pageable;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.idea.petclinicmanager.client.dto.ClientDTO;
import com.idea.petclinicmanager.client.entity.Client;
import com.idea.petclinicmanager.client.repository.IClientRepository;
import com.idea.petclinicmanager.user.UserThreadLocal;

@Service
public class ClientService implements IClientService {

    @Autowired
    IClientRepository repository;
    
    @Autowired
	private ModelMapper modelMapper;
    
    public Client insert(ClientDTO clientDTO) {
    	Client client = modelMapper.map(clientDTO, Client.class);
    	client.setUserId(UserThreadLocal.get().getId());
    	
    	repository.save(client);
    	return client;
    }
    
    public Client update(ClientDTO clientDTO, String clientId) {
    	Client client = modelMapper.map(clientDTO, Client.class);
    	client.setUserId(UserThreadLocal.get().getId());
    	client.setId(clientId);
    	repository.save(client);
    	return client;
    }
    
    public Page<Client> findAll(int page, int size) {
    	Pageable pageable = PageRequest.of(page, size);
    	return repository.findAll(UserThreadLocal.get().getId(), pageable);
    }
}
