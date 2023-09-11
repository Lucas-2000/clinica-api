package br.com.dev.clinica.domain.user;

import br.com.dev.clinica.domain.attendant.Attendant;
import br.com.dev.clinica.domain.doctor.Doctor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "users")
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, unique = true, columnDefinition = "uuid")
    private UUID id;

    @Column(nullable = false, length = 30, unique = true)
    private String username;

    @Column(nullable = false, length = 30)
    private String password;

    @Column(nullable = false)
    private UserRole role;

    @OneToOne(mappedBy = "user")
    private Doctor doctor;

    @OneToOne(mappedBy = "user")
    private Attendant attendant;

    public User(UserRequestDTO data) {
        this.username = data.username();
        this.password = data.password();
        this.role = data.role();
    }

    public User(UserResponseDTO data) {
        this.id = data.id();
        this.username = data.username();
        this.password = data.password();
        this.role = data.role();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == UserRole.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_ATTENDANT"), new SimpleGrantedAuthority("ROLE_DOCTOR"));
        else if(this.role == UserRole.DOCTOR) return List.of( new SimpleGrantedAuthority("ROLE_DOCTOR"));
        else return List.of( new SimpleGrantedAuthority("ROLE_ATTENDANT"));
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
