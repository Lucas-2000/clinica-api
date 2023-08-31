package br.com.dev.clinica.repositories;

import br.com.dev.clinica.domain.patient.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PatientRepository extends JpaRepository<Patient, UUID> {
}
