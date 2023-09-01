package br.com.dev.clinica.services;

import br.com.dev.clinica.domain.attendant.Attendant;
import br.com.dev.clinica.domain.attendant.AttendantRequestDTO;
import br.com.dev.clinica.domain.attendant.AttendantResponseDTO;
import br.com.dev.clinica.domain.user.User;
import br.com.dev.clinica.domain.user.UserResponseDTO;
import br.com.dev.clinica.domain.user.UserRole;
import br.com.dev.clinica.repositories.AttendantRepository;
import br.com.dev.clinica.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
                return findUserInfo(attendant);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
    }

    private AttendantResponseDTO findUserInfo(Attendant attendant) throws Exception {
        var userId = attendant.getUser().getId();

        Optional<User> user = userRepository.findById(userId);

        if (!user.isPresent()) throw new Exception();

        UserResponseDTO userResponseDTO = new UserResponseDTO(user.get().getId(), user.get().getUsername(), user.get().getPassword(), user.get().getRole());

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

}
