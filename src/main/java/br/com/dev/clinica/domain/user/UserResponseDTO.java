package br.com.dev.clinica.domain.user;

import java.util.UUID;

public record UserResponseDTO(UUID id, String username, String password, UserRole role) {
    public UserResponseDTO(User user) {
        this(user.getId(), user.getUsername(), user.getPassword(), user.getRole());
    }
}
