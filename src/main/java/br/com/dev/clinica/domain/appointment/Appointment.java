package br.com.dev.clinica.domain.appointment;

import br.com.dev.clinica.domain.doctor.Doctor;
import br.com.dev.clinica.domain.patient.Patient;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "appointments")
@Entity(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, unique = true)
    private UUID id;

    @Column(nullable = false, length = 500)
    private String description;

    @Column(nullable = false)
    private Date appointmentDatetime;

    private Date appointmentInitialDatetime;

    private Date appointmentFinishDatetime;

    @Column(nullable = false)
    private ConsultType consultType;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    private Integer rating;

    @Column(length = 500)
    private String review;

    public Appointment(AppointmentRequestDTO data) {
        this.description = data.description();
        this.appointmentDatetime = data.appointmentDatetime();
        this.appointmentInitialDatetime = data.appointmentInitialDatetime();
        this.appointmentFinishDatetime = data.appointmentFinishDatetime();
        this.consultType = data.consultType();
        this.patient = data.patient();
        this.doctor = data.doctor();
        this.rating = data.rating();
        this.review = data.review();
    }
}
