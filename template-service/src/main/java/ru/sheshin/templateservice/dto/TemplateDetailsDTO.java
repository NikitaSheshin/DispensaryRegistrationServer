package ru.sheshin.templateservice.dto;

import lombok.Data;

import java.util.Set;

@Data
public class TemplateDetailsDTO {
    private Long id;
    private String template_name;
    private String observation_period;
    private byte inspections_frequency;
    private long doctor_id;

    private Set<DiseaseDTO> diseases;
}
