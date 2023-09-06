package br.com.dev.clinica.domain.appointment;

import br.com.dev.clinica.domain.doctor.Doctor;
import br.com.dev.clinica.domain.patient.Patient;

import java.sql.Timestamp;
import java.util.Date;

public record AppointmentRequestDTO(
        String description,
        Timestamp appointmentDatetime,
        Timestamp appointmentInitialDatetime,
        Timestamp appointmentFinishDatetime,
        ConsultType consultType,
        Patient patient,
        Doctor doctor,
        Integer rating,
        String review
) {
}
