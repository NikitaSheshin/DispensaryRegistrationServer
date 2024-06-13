package ru.sheshin.visitservice.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class VisitDTO {
    private Long id;
    private Long patientId;
    private Long doctorId;
    private LocalDate visitDate;
    private String complaints;
    private String lifeAnamnesis;
    private String diseaseAnamnesis;
    private String recomendations;
}
