package ru.sheshin.patientservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sheshin.patientservice.dto.PatientDTO;
import ru.sheshin.patientservice.dto.PatientDetailsDTO;
import ru.sheshin.patientservice.dto.ReceptionInfoDTO;
import ru.sheshin.patientservice.service.PatientService;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/patients")
public class PatientController {
    private static final PatientService SERVICE = new PatientService();

    @GetMapping
    public ResponseEntity<List<PatientDTO>> getPatients(
    ) {
        return ResponseEntity.status(200)
                .body(SERVICE.getPatients());
    }

    @GetMapping("/{patient_id}")
    public PatientDetailsDTO getPatient(@PathVariable("patient_id") final Long patientId) {
        return SERVICE.getPatient(patientId);
    }

    @PostMapping
    public void addPatient(@RequestBody final PatientDTO requestDTO) {
        SERVICE.addPatient(requestDTO);
    }

    @PutMapping
    public void updatePatient(@RequestBody final PatientDTO patientToUpdate) {
        SERVICE.updatePatient(patientToUpdate);
    }

    @DeleteMapping("/{patient_id}")
    public void deletePatient(@PathVariable("patient_id") final Long patientId) {
        SERVICE.deletePatient(patientId);
    }

    @DeleteMapping("/inspection/{patient_id}")
    public void deleteInspection(
            @PathVariable("patient_id") final Long patientId
    ) {
        SERVICE.deleteObserveFromPatient(patientId);
    }

    @PostMapping("/inspection/{patient_id}")
    public void setTemplateToPatient(
            @PathVariable("patient_id") final Long patientId
    ) {
        SERVICE.setObserveToPatient(patientId);
    }

    @GetMapping("/todayReception")
    public ResponseEntity<List<ReceptionInfoDTO>> getTodayReceptionPatients(
    ) {
        return ResponseEntity
                .status(200)
                .body(
                        SERVICE.getTodayReceptions()
                );
    }

    @GetMapping("/searchByQuery")
    public ResponseEntity<List<ReceptionInfoDTO>> getPatientsByQuery(
            @RequestParam("query") final String query
    ) {
        return ResponseEntity
                .ok()
                .body(SERVICE.getPatientsByQuery(query));
    }
}
