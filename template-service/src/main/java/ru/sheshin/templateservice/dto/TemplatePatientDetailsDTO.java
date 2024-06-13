package ru.sheshin.templateservice.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class TemplatePatientDetailsDTO {
    private Long templateId;
    private String templateName;
    private LocalDate nextObservationDate;
    private Byte inspectionsFrequency;
    private List<String> diseasesNames;
}
