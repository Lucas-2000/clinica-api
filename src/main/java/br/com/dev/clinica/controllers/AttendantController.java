package br.com.dev.clinica.controllers;

import br.com.dev.clinica.domain.attendant.AttendantRequestDTO;
import br.com.dev.clinica.domain.attendant.AttendantResponseDTO;
import br.com.dev.clinica.services.AttendantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/attendants")
public class AttendantController {
    @Autowired
    AttendantService attendantService;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    public ResponseEntity<AttendantRequestDTO> create (@RequestBody AttendantRequestDTO data) {
        try {
            attendantService.create(data);
            return ResponseEntity.ok(data);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public ResponseEntity<List<AttendantResponseDTO>> findAll() {
        return ResponseEntity.ok(attendantService.findAll());
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("{id}")
    public ResponseEntity<AttendantResponseDTO> findById(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(attendantService.findById(id));
        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping("{id}")
    public ResponseEntity<AttendantRequestDTO> update(@PathVariable UUID id, @RequestBody AttendantRequestDTO data) {
        try {
            return ResponseEntity.ok(attendantService.update(id, data));
        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
