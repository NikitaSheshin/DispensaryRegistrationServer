package ru.sheshin.templateservice.dto;

import lombok.Data;

@Data
public class InspectionInfoDTO {
    private PatientDTO patientDTO;
    private String nextObservationDate;
}
