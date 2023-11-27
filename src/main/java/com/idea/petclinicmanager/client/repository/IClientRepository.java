package com.idea.petclinicmanager.client.repository;

import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import com.idea.petclinicmanager.client.entity.Client;

public interface IClientRepository extends JpaRepository<Client, String> {
	Page<Client> findAll(String userId, Pageable pageable);
}
