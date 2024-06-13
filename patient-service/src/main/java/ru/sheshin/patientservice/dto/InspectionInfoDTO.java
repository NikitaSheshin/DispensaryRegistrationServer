package ru.sheshin.patientservice.dto;

import lombok.Data;

@Data
public class InspectionInfoDTO {
    private PatientDTO patientDTO;
    private String nextObservationDate;
}
