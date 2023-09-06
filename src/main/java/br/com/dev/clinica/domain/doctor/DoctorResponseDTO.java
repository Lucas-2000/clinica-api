package br.com.dev.clinica.domain.doctor;

import br.com.dev.clinica.domain.attendant.Attendant;
import br.com.dev.clinica.domain.user.UserResponseDTO;

import java.util.Date;
import java.util.UUID;

public record DoctorResponseDTO(
        UUID id,
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
        String[] workDays,
        String[] workHours,
        String[] specialties,
        Boolean isActive,
        UserResponseDTO user
) {
    public DoctorResponseDTO(Doctor doctor) {
        this(
                doctor.getId(),
                doctor.getCpf(),
                doctor.getCrm(),
                doctor.getFirstName(),
                doctor.getLastName(),
                doctor.getBirthdate(),
                doctor.getStreet(),
                doctor.getNumber(),
                doctor.getCity(),
                doctor.getUf(),
                doctor.getCellphone(),
                doctor.getEmail(),
                doctor.getWorkDays(),
                doctor.getWorkHours(),
                doctor.getSpecialties(),
                doctor.isActive(),
                new UserResponseDTO(
                        doctor.getUser().getId(),
                        doctor.getUser().getUsername(),
                        doctor.getUser().getPassword(),
                        doctor.getUser().getRole()
                )
        );
    }
}
