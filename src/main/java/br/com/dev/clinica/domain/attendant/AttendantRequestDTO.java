package br.com.dev.clinica.domain.attendant;

import java.util.Date;
import java.util.UUID;

public record AttendantRequestDTO(
        String cpf,
        String firstName,
        String lastName,
        Date birthdate,
        String street,
        Integer number,
        String city,
        String uf,
        String cellphone,
        String email,
        Boolean isActive
    ) {
}
