package br.com.dev.clinica.domain.user;

import br.com.dev.clinica.domain.attendant.Attendant;
import br.com.dev.clinica.domain.doctor.Doctor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    private String id;

    @Column(nullable = false, length = 30)
    private String username;

    @Column(nullable = false, length = 30)
    private String password;

    @Column(nullable = false)
    private UserRole role;

    @OneToOne(mappedBy = "users")
    private Doctor doctor;

    @OneToOne(mappedBy = "users")
    private Attendant attendant;
}
