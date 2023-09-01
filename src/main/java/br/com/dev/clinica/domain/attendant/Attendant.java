package br.com.dev.clinica.domain.attendant;

import br.com.dev.clinica.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "attendants")
@Entity(name = "attendants")
public class Attendant {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String cpf;

    @Column(nullable = false, length = 30)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

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
    private boolean isActive;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Attendant(AttendantRequestDTO data) {
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
        this.isActive = data.isActive();
    }
}
