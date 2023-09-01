package br.com.dev.clinica.domain.user;

public enum UserRole {
    ADMIN("admin"),
    ATTENDANT("attendant"),
    DOCTOR("doctor");

    private String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}
