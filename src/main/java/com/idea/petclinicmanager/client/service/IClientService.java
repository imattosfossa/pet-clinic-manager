package com.idea.petclinicmanager.client.service;

import org.springframework.data.domain.Page;

import com.idea.petclinicmanager.client.dto.ClientDTO;
import com.idea.petclinicmanager.client.entity.Client;

public interface IClientService {
	public Client insert(ClientDTO clientDTO);
    public Client update(ClientDTO clientDTO, String clientId);
    public Page<Client> findAll(int page, int size, String document, String name, String email, String sortField, String sortType);
}