package br.com.dev.clinica.repositories;

import br.com.dev.clinica.domain.attendant.Attendant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AttendantRepository extends JpaRepository<Attendant, UUID> {
}
