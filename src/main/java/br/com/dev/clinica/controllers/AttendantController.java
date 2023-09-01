package br.com.dev.clinica.controllers;

import br.com.dev.clinica.domain.attendant.AttendantRequestDTO;
import br.com.dev.clinica.domain.attendant.AttendantResponseDTO;
import br.com.dev.clinica.services.AttendantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

}
