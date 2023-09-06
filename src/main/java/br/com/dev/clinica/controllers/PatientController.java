package br.com.dev.clinica.controllers;

import br.com.dev.clinica.domain.patient.Patient;
import br.com.dev.clinica.domain.patient.PatientRequestDTO;
import br.com.dev.clinica.domain.patient.PatientResponseDTO;
import br.com.dev.clinica.domain.user.UserResponseDTO;
import br.com.dev.clinica.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/patients")
public class PatientController {

    @Autowired
    PatientRepository patientRepository;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    public ResponseEntity<PatientRequestDTO> create(@RequestBody PatientRequestDTO data) {
        try {
            Patient patient = new Patient(data);

            patient.setHealthInsurance(data.healthInsurance().toLowerCase());

            patientRepository.save(patient);
            return ResponseEntity.ok(data);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public ResponseEntity<List<PatientResponseDTO>> findAll() {
        return ResponseEntity.ok(patientRepository.findAll().stream().map(PatientResponseDTO::new).toList());
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("{id}")
    public ResponseEntity<PatientResponseDTO> findById(@PathVariable UUID id) {
        Optional<Patient> patient = patientRepository.findById(id);

        if(patient.isPresent()) {
            PatientResponseDTO patientResponseDTO = new PatientResponseDTO(patient.get());
            return ResponseEntity.ok(patientResponseDTO);
        }

        return ResponseEntity.notFound().build();
    }


    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping("{id}")
    public ResponseEntity<PatientResponseDTO> update(@PathVariable UUID id, @RequestBody PatientRequestDTO data) {
        Optional<Patient> patient = patientRepository.findById(id);

        if(patient.isPresent()) {
            Patient newPatient = patient.get();

            newPatient.setCpf(data.cpf());
            newPatient.setFirstName(data.firstName());
            newPatient.setLastName(data.lastName());
            newPatient.setBirthdate(data.birthdate());
            newPatient.setStreet(data.street());
            newPatient.setNumber(data.number());
            newPatient.setCity(data.city());
            newPatient.setUf(data.uf());
            newPatient.setCellphone(data.cellphone());
            newPatient.setEmail(data.email());
            newPatient.setHealthInsurance(data.healthInsurance());
            newPatient.setHealthInsuranceCode(data.healthInsuranceCode());

            patientRepository.save(newPatient);

            PatientResponseDTO patientResponseDTO = new PatientResponseDTO(newPatient);

            return ResponseEntity.ok(patientResponseDTO);
        }

        return ResponseEntity.notFound().build();
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable UUID id) {
        Optional<Patient> patient = patientRepository.findById(id);

        if(patient.isPresent()) {
            patientRepository.deleteById(id);
            return ResponseEntity.ok("Patient deleted successfully");
        }

        return ResponseEntity.notFound().build();
    }
}
