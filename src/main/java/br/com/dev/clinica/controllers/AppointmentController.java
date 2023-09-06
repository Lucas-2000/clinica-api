package br.com.dev.clinica.controllers;

import br.com.dev.clinica.domain.appointment.AppointmentRequestDTO;
import br.com.dev.clinica.domain.appointment.AppointmentResponseDTO;
import br.com.dev.clinica.services.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    AppointmentService appointmentService;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    public ResponseEntity<AppointmentResponseDTO> create(@RequestBody AppointmentRequestDTO data) {
        AppointmentResponseDTO appointment = appointmentService.create(data);
        return ResponseEntity.ok(appointment);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public ResponseEntity<List<AppointmentResponseDTO>> findAll() {
        return ResponseEntity.ok(appointmentService.findAll());
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("{id}")
    public ResponseEntity<AppointmentResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(appointmentService.findById(id));
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping("{id}")
    public ResponseEntity<AppointmentResponseDTO> update(@PathVariable UUID id, @RequestBody AppointmentRequestDTO data) {
        return ResponseEntity.ok(appointmentService.update(id, data));
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping("{id}")
    public ResponseEntity<String> update(@PathVariable UUID id) {
        appointmentService.delete(id);
        return ResponseEntity.ok("Appointment deleted successfully");
    }
}
