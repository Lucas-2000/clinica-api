package br.com.dev.clinica.domain.doctor;

import br.com.dev.clinica.domain.appointment.Appointment;
import br.com.dev.clinica.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "doctors")
@Entity(name = "doctors")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    private String id;

    @Column(nullable = false, unique = true)
    private String cpf;

    @Column(nullable = false, unique = true)
    private String crm;

    @Column(nullable = false, length = 30)
    private String first_name;

    @Column(nullable = false, length = 50)
    private String last_name;

    @Column(nullable = false)
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

    @Column(nullable = false)
    private String[] specialties;

    @Column(nullable = false)
    private boolean isActive;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "doctors")
    private List<Appointment> appointments;
}
