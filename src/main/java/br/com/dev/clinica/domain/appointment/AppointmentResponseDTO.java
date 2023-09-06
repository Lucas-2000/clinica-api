package br.com.dev.clinica.domain.appointment;

import br.com.dev.clinica.domain.doctor.Doctor;
import br.com.dev.clinica.domain.patient.Patient;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

public record AppointmentResponseDTO(
        UUID id,
        String description,
        Timestamp appointmentDatetime,
        Timestamp appointmentInitialDatetime,
        Timestamp appointmentFinishDatetime,
        ConsultType consultType,
        UUID patientId,
        UUID doctorId,
        Integer rating,
        String review
) {
    public AppointmentResponseDTO(Appointment appointment) {
        this(
          appointment.getId(),
          appointment.getDescription(),
          appointment.getAppointmentDatetime(),
          appointment.getAppointmentInitialDatetime(),
          appointment.getAppointmentFinishDatetime(),
          appointment.getConsultType(),
          appointment.getPatient().getId(),
          appointment.getDoctor().getId(),
          appointment.getRating(),
          appointment.getReview()
        );
    }
}
