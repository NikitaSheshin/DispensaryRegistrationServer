package ru.sheshin.templateservice.controller;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sheshin.templateservice.dto.*;
import ru.sheshin.templateservice.service.TemplateService;

import java.io.File;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/templates")
public class TemplateController {
    private final TemplateService TEMPLATE_SERVICE = new TemplateService();

    @GetMapping
    public ResponseEntity<List<FoundTemplateDTO>> getTemplatesByRequest(
            @RequestParam("doctor_id") final Long id,
            @RequestParam(value = "request", required = false) final String request
    ) {
        return ResponseEntity.status(200)
                .body(TEMPLATE_SERVICE.getTemplatesByRequest(id, request));
    }

    @GetMapping("/{template_id}")
    public ResponseEntity<TemplateDetailsDTO> getTemplateById(
            @RequestParam("doctor_id") final Long doctorId,
            @PathVariable("template_id") final Long templateId
    ) {
        return ResponseEntity.status(200)
                .body(TEMPLATE_SERVICE.getTemplateById(doctorId, templateId));
    }

    @PostMapping
    public void addTemplate(@RequestBody final TemplateSimpleDTO requestDTO) {
        TEMPLATE_SERVICE.addTemplate(requestDTO);
    }

    @PutMapping("/{template_id}")
    public void updateTemplate(@RequestBody final TemplateSimpleDTO templateToUpdate,
                               @PathVariable("template_id") final Long templateId) {
        TEMPLATE_SERVICE.updateTemplate(templateId, templateToUpdate);
    }

    @DeleteMapping("/{template_id}")
    public void deleteTemplate(@PathVariable("template_id") final Long templateId) {
        TEMPLATE_SERVICE.deleteTemplate(templateId);
    }

    @GetMapping("/byPatientAndDoctor")
    public ResponseEntity<TemplatePatientDetailsDTO> getTemplateByPatientAndDoctorIds(
            @RequestParam("patient_id") final Long patientId,
            @RequestParam("doctor_id") final Long doctorId
    ) {
        return ResponseEntity
                .ok()
                .body(TEMPLATE_SERVICE.getTemplateByPatientAndDoctorIds(patientId, doctorId));
    }

    @PutMapping("/updateObservationDate")
    public void updateObservationDate(@RequestBody final
                                          UpdateObservationDateDto updateObservationDateDto) {
        TEMPLATE_SERVICE.updateNextObservationDate(updateObservationDateDto);
    }

    @PostMapping("/inspection")
    public void setTemplateToPatient(
            @RequestParam("patient_id") final Long patientId,
            @RequestParam("template_id") final Long templateId
    ) {
        TEMPLATE_SERVICE.setTemplateToPatient(patientId, templateId);
    }

    @DeleteMapping("/inspection")
    public void deleteInspection(
            @RequestParam("patient_id") final Long patientId,
            @RequestParam("template_id") final Long templateId
    ) {
        TEMPLATE_SERVICE.deleteInspection(patientId, templateId);
    }

    @GetMapping("/inspectionFile")
    public ResponseEntity<FileSystemResource> getReceptionFile(@RequestParam("doctor_id") final String doctorId,
                                                               @RequestParam("from_date") final String fromDate,
                                                               @RequestParam("to_date") final String toDate) {
        TEMPLATE_SERVICE.getInspectionsInFile(Long.parseLong(doctorId), fromDate, toDate);
        File file = new File("/Пациенты_список.pdf");

        if (!file.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        FileSystemResource resource = new FileSystemResource(file);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());
        headers.add(HttpHeaders.CONTENT_TYPE, "application/pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }

    @GetMapping("/getPatientsBetweenDate")
    public ResponseEntity<List<InspectionInfoDTO>> getPatientsWithInspectionBetweenDate(
            @RequestParam("doctor_id") final String doctorId,
            @RequestParam("from_date") final String fromDate,
            @RequestParam("to_date") final String toDate
    ) {
        return ResponseEntity
                .status(200)
                .body(
                        TEMPLATE_SERVICE.getPatientsWithInspectionBetweenDate(
                                Long.parseLong(doctorId), fromDate, toDate
                        ));
    }
}
