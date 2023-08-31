package br.com.dev.clinica.repositories;

import br.com.dev.clinica.domain.doctor.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DoctorRepository extends JpaRepository<Doctor, UUID> {
}
