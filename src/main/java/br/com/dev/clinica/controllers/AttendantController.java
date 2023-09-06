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
        attendantService.create(data);
        return ResponseEntity.ok(data);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public ResponseEntity<List<AttendantResponseDTO>> findAll() {
        return ResponseEntity.ok(attendantService.findAll());
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("{id}")
    public ResponseEntity<AttendantResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(attendantService.findById(id));
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping("{id}")
    public ResponseEntity<AttendantRequestDTO> update(@PathVariable UUID id, @RequestBody AttendantRequestDTO data) {
        return ResponseEntity.ok(attendantService.update(id, data));
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        attendantService.delete(id);
        return ResponseEntity.ok("Attendant deleted successfully");
    }
}
