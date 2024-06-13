package ru.sheshin.visitservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sheshin.visitservice.dto.VisitDTO;
import ru.sheshin.visitservice.service.VisitService;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/visits")
public class VisitController {
    private static final VisitService SERVICE = new VisitService();

    @PostMapping
    public void addVisit(@RequestBody final VisitDTO visitDTO) {
        SERVICE.addVisit(visitDTO);
    }

    @GetMapping("/{doctor_id}/{patient_id}")
    public ResponseEntity<List<VisitDTO>> getVisitsByPatientID(
            @PathVariable("doctor_id") final Long doctorId,
            @PathVariable("patient_id") final Long patientId) {
        return ResponseEntity
                .ok()
                .body(SERVICE.getVisitByPatient(doctorId, patientId));
    }

    @GetMapping("/{visit_id}")
    public ResponseEntity<VisitDTO> getVisitById(
            @PathVariable("visit_id") final Long visitId
    ) {
        return ResponseEntity
                .ok()
                .body(SERVICE.getVisitById(visitId));
    }
}
