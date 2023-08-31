package br.com.dev.clinica.repositories;

import br.com.dev.clinica.domain.appointment.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {
}
