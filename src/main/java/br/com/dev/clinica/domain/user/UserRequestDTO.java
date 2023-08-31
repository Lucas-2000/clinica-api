package br.com.dev.clinica.domain.user;

public record UserRequestDTO(String username, String password, UserRole role) {
}
