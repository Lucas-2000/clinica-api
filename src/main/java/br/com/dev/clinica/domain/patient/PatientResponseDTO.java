package br.com.dev.clinica.domain.patient;

import java.util.Date;
import java.util.UUID;

public record PatientResponseDTO(
        UUID id,
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
    public PatientResponseDTO(Patient patient) {
        this(
                patient.getId(),
                patient.getCpf(),
                patient.getFirstName(),
                patient.getLastName(),
                patient.getBirthdate(),
                patient.getStreet(),
                patient.getNumber(),
                patient.getCity(),
                patient.getUf(),
                patient.getCellphone(),
                patient.getEmail(),
                patient.getHealthInsurance(),
                patient.getHealthInsuranceCode()
        );
    }
}
