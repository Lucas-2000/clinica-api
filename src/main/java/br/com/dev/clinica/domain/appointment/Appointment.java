package br.com.dev.clinica.domain.appointment;

import br.com.dev.clinica.domain.doctor.Doctor;
import br.com.dev.clinica.domain.patient.Patient;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "appointments")
@Entity(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    private String id;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Date appointment_initial_datetime;

    private Date appointment_finish_datetime;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    private Integer rating;

    private String review;
}
