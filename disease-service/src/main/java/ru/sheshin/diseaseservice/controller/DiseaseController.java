package ru.sheshin.diseaseservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sheshin.diseaseservice.dto.DiseaseDTO;
import ru.sheshin.diseaseservice.service.DiseaseService;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/diseases")
public class DiseaseController {
    private final DiseaseService DISEASE_SERVICE = new DiseaseService();

    @GetMapping
    public ResponseEntity<List<DiseaseDTO>> getDiseases() {
        return ResponseEntity.status(200)
                .body(DISEASE_SERVICE.getDiseases());
    }

    @GetMapping("/{disease_id}")
    public DiseaseDTO getDiseaseById(@PathVariable("disease_id") final Long diseaseId) {
        return DISEASE_SERVICE.getDiseaseById(diseaseId);
    }
}
