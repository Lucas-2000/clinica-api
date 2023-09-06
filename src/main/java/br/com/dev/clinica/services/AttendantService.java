package br.com.dev.clinica.services;

import br.com.dev.clinica.domain.attendant.Attendant;
import br.com.dev.clinica.domain.attendant.AttendantRequestDTO;
import br.com.dev.clinica.domain.attendant.AttendantResponseDTO;
import br.com.dev.clinica.domain.user.User;
import br.com.dev.clinica.domain.user.UserResponseDTO;
import br.com.dev.clinica.domain.user.UserRole;
import br.com.dev.clinica.infra.exceptions.NotFoundException;
import br.com.dev.clinica.repositories.AttendantRepository;
import br.com.dev.clinica.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static br.com.dev.clinica.utils.string.UtilsString.generateRandomPassword;

@Service
public class AttendantService {

    @Autowired
    AttendantRepository attendantRepository;

    @Autowired
    UserRepository userRepository;

    @Transactional
    public AttendantRequestDTO create(AttendantRequestDTO data) {
        Attendant attendant = new Attendant(data);

        User user = generateNewUser(data.firstName(), data.lastName());

        attendant.setActive(true);
        attendant.setUser(user);

        attendantRepository.save(attendant);

       return data;
    }

    @Transactional
    private User generateNewUser(String firstName, String lastName) {
        String fullUsername = firstName.replaceAll("\\s", "").toLowerCase() + "." + lastName.replaceAll("\\s", "").toLowerCase();

        User user = new User();

        UserDetails existingUser = userRepository.findByUsername(fullUsername);

        if (existingUser != null) {
            int sequenceNumber = 1;
            String newUsername = fullUsername;

            while (userRepository.findByUsername(newUsername) != null) {
                sequenceNumber++;
                newUsername = fullUsername + sequenceNumber;
            }

            fullUsername = newUsername;
        }

        user.setUsername(fullUsername);
        user.setRole(UserRole.ATTENDANT);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String password = passwordEncoder.encode(generateRandomPassword(10));

        user.setPassword(password);

        userRepository.save(user);

        return user;
    }

    public List<AttendantResponseDTO> findAll() {
        return attendantRepository.findAll().stream().map(attendant -> {
            try {
                return buildAttendantInfo(attendant);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
    }

    private AttendantResponseDTO buildAttendantInfo(Attendant attendant) throws Exception {
        var userId = attendant.getUser().getId();

        UserResponseDTO userResponseDTO = findUserInfo(userId);

        return new AttendantResponseDTO(
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
                userResponseDTO
        );
    }

    private UserResponseDTO findUserInfo(UUID userId) throws RuntimeException {
        Optional<User> user = userRepository.findById(userId);

        if (!user.isPresent()) throw new NotFoundException("User not found");

        UserResponseDTO userResponseDTO = new UserResponseDTO(user.get().getId(), user.get().getUsername(), user.get().getPassword(), user.get().getRole());

        return userResponseDTO;
    }


    public AttendantResponseDTO findById(UUID id) throws RuntimeException {
        Optional<Attendant> attendant = attendantRepository.findById(id);

        if(!attendant.isPresent()) throw new NotFoundException("Attendant not found");

        UserResponseDTO userResponseDTO = findUserInfo(attendant.get().getUser().getId());

        AttendantResponseDTO attendantResponseDTO = new AttendantResponseDTO(
                attendant.get().getId(),
                attendant.get().getCpf(),
                attendant.get().getFirstName(),
                attendant.get().getLastName(),
                attendant.get().getBirthdate(),
                attendant.get().getStreet(),
                attendant.get().getNumber(),
                attendant.get().getCity(),
                attendant.get().getUf(),
                attendant.get().getCellphone(),
                attendant.get().getEmail(),
                attendant.get().isActive(),
                userResponseDTO
        );

        return attendantResponseDTO;
    }

    public AttendantRequestDTO update(UUID id, AttendantRequestDTO data) throws RuntimeException {
        Optional<Attendant> attendant = attendantRepository.findById(id);

        if(!attendant.isPresent()) throw new NotFoundException("Attendant not found");

        Attendant existingAttendant = attendant.get();

        existingAttendant.setCpf(data.cpf());
        existingAttendant.setFirstName(data.firstName());
        existingAttendant.setLastName(data.lastName());
        existingAttendant.setBirthdate(data.birthdate());
        existingAttendant.setStreet(data.street());
        existingAttendant.setNumber(data.number());
        existingAttendant.setCity(data.city());
        existingAttendant.setUf(data.uf());
        existingAttendant.setCellphone(data.cellphone());
        existingAttendant.setEmail(data.email());
        existingAttendant.setActive(data.isActive());

        attendantRepository.save(existingAttendant);

        return data;
    }

    public void delete(UUID id) throws RuntimeException {
        Optional<Attendant> attendant = attendantRepository.findById(id);

        if(!attendant.isPresent()) throw new NotFoundException("Attendant not found");

        Attendant existingAttendant = attendant.get();

        attendantRepository.delete(existingAttendant);

        userRepository.delete(existingAttendant.getUser());
    }
}
