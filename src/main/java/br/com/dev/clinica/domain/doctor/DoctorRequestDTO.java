package br.com.dev.clinica.domain.doctor;

import java.util.Date;

public record DoctorRequestDTO(
        String cpf,
        String crm,
        String firstName,
        String lastName,
        Date birthdate,
        String street,
        Integer number,
        String city,
        String uf,
        String cellphone,
        String email,
        String[] specialties,
        Boolean isActive
) {
}
