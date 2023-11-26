package com.idea.petclinicmanager.security.dto;

import java.util.Date;

import com.idea.petclinicmanager.user.entity.UserRole;

public record RegisterDTO(String email, String password, UserRole role, Date dateOfBirth, String document, String name) {
}
