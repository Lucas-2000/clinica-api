package br.com.dev.clinica.controllers;

import br.com.dev.clinica.domain.user.*;
import br.com.dev.clinica.infra.security.TokenService;
import br.com.dev.clinica.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    public ResponseEntity<UserRequestDTO> create(@RequestBody UserRequestDTO data) {
        try {
            User user = new User(data);

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            String password = passwordEncoder.encode(data.password());

            user.setPassword(password);

            userRepository.save(user);
            return new ResponseEntity<>(data, HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> findAll() {
        return ResponseEntity.ok(userRepository.findAll().stream().map(UserResponseDTO::new).toList());
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping(value = "{id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable UUID id) {
        Optional<User> user = userRepository.findById(id);

        if(user.isPresent()) {
            UserResponseDTO userResponseDTO = new UserResponseDTO(user.get());
            return ResponseEntity.ok(userResponseDTO);
        }

        return ResponseEntity.notFound().build();
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping(value = "{id}")
    public ResponseEntity<UserResponseDTO> update(@PathVariable UUID id, @RequestBody UserRequestDTO data) {
        Optional<User> user = userRepository.findById(id);

        if(user.isPresent()) {
            User newUser = user.get();

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            String password = passwordEncoder.encode(data.password());

            newUser.setUsername(data.username());
            newUser.setPassword(password);
            newUser.setRole(data.role());

            userRepository.save(newUser);
            UserResponseDTO userResponseDTO = new UserResponseDTO(newUser);
            return ResponseEntity.ok(userResponseDTO);
        }

        return ResponseEntity.notFound().build();
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping(value = "{id}")
    public ResponseEntity delete(@PathVariable UUID id) {
        Optional<User> user = userRepository.findById(id);

        if(user.isPresent()) {
            userRepository.deleteById(id);
            return ResponseEntity.ok("User deleted successfully");
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());

        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
}
