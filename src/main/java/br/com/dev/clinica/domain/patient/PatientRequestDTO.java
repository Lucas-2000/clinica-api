package br.com.dev.clinica.domain.patient;

import java.util.Date;

public record PatientRequestDTO(
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
        String healthInsurance,
        String healthInsuranceCode
) {
}
