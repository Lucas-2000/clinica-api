package br.com.dev.clinica.services;

import br.com.dev.clinica.domain.doctor.Doctor;
import br.com.dev.clinica.domain.doctor.DoctorRequestDTO;
import br.com.dev.clinica.domain.doctor.DoctorResponseDTO;
import br.com.dev.clinica.domain.user.User;
import br.com.dev.clinica.domain.user.UserResponseDTO;
import br.com.dev.clinica.domain.user.UserRole;
import br.com.dev.clinica.infra.exceptions.NotFoundException;
import br.com.dev.clinica.repositories.DoctorRepository;
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
public class DoctorService {

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    UserRepository userRepository;

    @Transactional
    public DoctorRequestDTO create(DoctorRequestDTO data) {
        Doctor doctor = new Doctor(data);

        User user = generateNewUser(data.firstName(), data.lastName());

        doctor.setActive(true);
        doctor.setUser(user);

        doctorRepository.save(doctor);

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
        user.setRole(UserRole.DOCTOR);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String password = passwordEncoder.encode(generateRandomPassword(10));

        user.setPassword(password);

        userRepository.save(user);

        return user;
    }

    public List<DoctorResponseDTO> findAll() {
        return doctorRepository.findAll().stream().map(doctor -> {
            try {
                return buildAttendantInfo(doctor);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
    }

    private DoctorResponseDTO buildAttendantInfo(Doctor doctor) throws Exception {
        var userId = doctor.getUser().getId();

        UserResponseDTO userResponseDTO = findUserInfo(userId);

        return new DoctorResponseDTO(
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
                doctor.getSpecialties(),
                doctor.isActive(),
                userResponseDTO
        );
    }

    private UserResponseDTO findUserInfo(UUID userId) throws Exception {
        Optional<User> user = userRepository.findById(userId);

        if (!user.isPresent()) throw new NotFoundException("User not found");

        return new UserResponseDTO(user.get().getId(), user.get().getUsername(), user.get().getPassword(), user.get().getRole());
    }

    public DoctorResponseDTO findById(UUID id) throws Exception {
        Optional<Doctor> doctor = doctorRepository.findById(id);

        if(!doctor.isPresent()) throw new NotFoundException("Doctor not found");

        UserResponseDTO userResponseDTO = findUserInfo(doctor.get().getUser().getId());

        DoctorResponseDTO doctorResponseDTO = new DoctorResponseDTO(
                doctor.get().getId(),
                doctor.get().getCpf(),
                doctor.get().getCrm(),
                doctor.get().getFirstName(),
                doctor.get().getLastName(),
                doctor.get().getBirthdate(),
                doctor.get().getStreet(),
                doctor.get().getNumber(),
                doctor.get().getCity(),
                doctor.get().getUf(),
                doctor.get().getCellphone(),
                doctor.get().getEmail(),
                doctor.get().getSpecialties(),
                doctor.get().isActive(),
                userResponseDTO
        );

        return doctorResponseDTO;
    }

    public DoctorRequestDTO update(UUID id, DoctorRequestDTO data) throws Exception {
        Optional<Doctor> doctor = doctorRepository.findById(id);

        if(!doctor.isPresent()) throw new NotFoundException("Doctor not found");

        Doctor existingDoctor = doctor.get();

        existingDoctor.setCpf(data.cpf());
        existingDoctor.setCrm(data.crm());
        existingDoctor.setFirstName(data.firstName());
        existingDoctor.setLastName(data.lastName());
        existingDoctor.setBirthdate(data.birthdate());
        existingDoctor.setStreet(data.street());
        existingDoctor.setNumber(data.number());
        existingDoctor.setCity(data.city());
        existingDoctor.setUf(data.uf());
        existingDoctor.setCellphone(data.cellphone());
        existingDoctor.setEmail(data.email());
        existingDoctor.setSpecialties(data.specialties());
        existingDoctor.setActive(data.isActive());

        doctorRepository.save(existingDoctor);

        return data;
    }

    public void delete(UUID id) throws Exception {
        Optional<Doctor> doctor = doctorRepository.findById(id);

        if(!doctor.isPresent()) throw new NotFoundException("Doctor not found");

        Doctor existingDoctor = doctor.get();

        doctorRepository.delete(existingDoctor);

        userRepository.delete(existingDoctor.getUser());
    }
}
