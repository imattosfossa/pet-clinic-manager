package com.idea.petclinicmanager.client.service;

import com.idea.petclinicmanager.client.dto.ClientDTO;
import com.idea.petclinicmanager.client.entity.Client;

public interface IClientService {
	public Client insert(ClientDTO clientDTO);
    public ClientDTO update(ClientDTO clientDTO, String clientId);
    public ClientDTO findOneById(String clientId);
    public ClientDTO findAll(String userId);
}