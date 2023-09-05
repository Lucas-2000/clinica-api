package br.com.dev.clinica.domain.appointment;

import br.com.dev.clinica.domain.doctor.Doctor;
import br.com.dev.clinica.domain.patient.Patient;

import java.util.Date;

public record AppointmentRequestDTO(
        String description,
        Date appointmentDatetime,
        Date appointmentInitialDatetime,
        Date appointmentFinishDatetime,
        ConsultType consultType,
        Patient patient,
        Doctor doctor,
        Integer rating,
        String review
) {
}
