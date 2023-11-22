package com.idea.petclinicmanager.security.dto;

import com.idea.petclinicmanager.user.entity.UserRole;

public record RegisterDTO(String email, String password, UserRole role) {
}
