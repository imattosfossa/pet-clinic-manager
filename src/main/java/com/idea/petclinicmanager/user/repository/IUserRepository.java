package com.idea.petclinicmanager.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.idea.petclinicmanager.user.entity.User;

public interface IUserRepository extends JpaRepository<User, String> {
    UserDetails findByEmail(String email);
}
