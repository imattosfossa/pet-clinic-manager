package com.idea.petclinicmanager.client.service;

import java.util.Date;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.idea.petclinicmanager.client.dto.ClientDTO;
import com.idea.petclinicmanager.client.entity.Client;
import com.idea.petclinicmanager.client.repository.IClientRepository;
import com.idea.petclinicmanager.exceptions.BusinessException;
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
    	client.setCreateDate(new Date());
    	repository.save(client);
    	return client;
    }
    
    public Client update(ClientDTO clientDTO, String clientId) {
    	Optional<Client> optionalExistClient = repository.findById(clientId);
    	if(!optionalExistClient.isPresent()) {
    		throw new BusinessException("Client not found.");
    	}
    	
    	Client client = modelMapper.map(clientDTO, Client.class);
    	client.setUserId(UserThreadLocal.get().getId());
    	client.setId(clientId);
    	client.setCreateDate(optionalExistClient.get().getCreateDate());
    	
    	repository.save(client);
    	return client;
    }
    
    public Page<Client> findAll(int page, int size, String document, String name, String email, String sortField, String sortType) {
    	Sort sort = Sort.by(sortType, sortField);
    	
    	Pageable pageable = PageRequest.of(page, size, sort);
    	
    	Client clientFilter = new Client();
    	clientFilter.setUserId(UserThreadLocal.get().getId());

        if (document != null && !document.isEmpty()) {
        	clientFilter.setDocument(document);
        }

        if (email != null && !email.isEmpty()) {
        	clientFilter.setEmail(email);
        }

        if (name != null && !name.isEmpty()) {
        	clientFilter.setName(name);
        }

    	return repository.findAll(Example.of(clientFilter), pageable);
    }
}
