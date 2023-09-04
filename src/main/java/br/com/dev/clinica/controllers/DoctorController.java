package br.com.dev.clinica.controllers;

import br.com.dev.clinica.domain.doctor.DoctorRequestDTO;
import br.com.dev.clinica.domain.doctor.DoctorResponseDTO;
import br.com.dev.clinica.services.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    @Autowired
    DoctorService doctorService;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    public ResponseEntity<DoctorRequestDTO> create (@RequestBody DoctorRequestDTO data) {
        try {
            doctorService.create(data);
            return ResponseEntity.ok(data);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public ResponseEntity<List<DoctorResponseDTO>> findAll () {
        return ResponseEntity.ok(doctorService.findAll());
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("{id}")
    public ResponseEntity<DoctorResponseDTO> findById (@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(doctorService.findById(id));
        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping("{id}")
    public ResponseEntity<DoctorRequestDTO> update (@PathVariable UUID id, @RequestBody DoctorRequestDTO data) {
        try {
            return ResponseEntity.ok(doctorService.update(id, data));
        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping("{id}")
    public ResponseEntity<String> delete (@PathVariable UUID id) {
        try {
            doctorService.delete(id);
            return ResponseEntity.ok("Doctor deleted successfully");
        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
