package ru.sheshin.templateservice.dto;

import lombok.Data;

@Data
public class DiseaseDTO {
    private Long id;
    private String icd_id;
    private String disease_name;
}
