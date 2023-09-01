package br.com.dev.clinica.domain.attendant;

import br.com.dev.clinica.domain.user.User;
import br.com.dev.clinica.domain.user.UserResponseDTO;

import java.util.Date;
import java.util.UUID;

public record AttendantResponseDTO(
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
        Boolean isActive,
        UserResponseDTO user
) {
    public AttendantResponseDTO(Attendant attendant) {
        this(
                attendant.getId(),
                attendant.getCpf(),
                attendant.getFirstName(),
                attendant.getLastName(),
                attendant.getBirthdate(),
                attendant.getStreet(),
                attendant.getNumber(),
                attendant.getCity(),
                attendant.getUf(),
                attendant.getCellphone(),
                attendant.getEmail(),
                attendant.isActive(),
                new UserResponseDTO(
                        attendant.getUser().getId(),
                        attendant.getUser().getUsername(),
                        attendant.getUser().getPassword(),
                        attendant.getUser().getRole()
                )
        );
    }
}
