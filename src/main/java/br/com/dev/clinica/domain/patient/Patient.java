package br.com.dev.clinica.domain.patient;

import br.com.dev.clinica.domain.appointment.Appointment;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "patients")
@Entity(name = "patients")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, unique = true)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String cpf;

    @Column(nullable = false, length = 30)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT")
    private Date birthdate;

    @Column(nullable = false, length = 100)
    private String street;

    @Column(nullable = false)
    private Integer number;

    @Column(nullable = false, length = 30)
    private String city;

    @Column(nullable = false, length = 2)
    private String uf;

    @Column(nullable = false, length = 16)
    private String cellphone;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false, length = 30)
    private String healthInsurance;

    @Column(nullable = false, length = 50)
    private String healthInsuranceCode;

    @OneToMany(mappedBy = "patient")
    private List<Appointment> appointments;

    public Patient(PatientRequestDTO data) {
        this.cpf = data.cpf();
        this.firstName = data.firstName();
        this.lastName = data.lastName();
        this.birthdate = data.birthdate();
        this.street = data.street();
        this.number = data.number();
        this.city = data.city();
        this.uf = data.uf();
        this.cellphone = data.cellphone();
        this.email = data.email();
        this.healthInsurance = data.healthInsurance();
        this.healthInsuranceCode = data.healthInsuranceCode();
    }
}
