package ru.sheshin.templateservice.dto;

import lombok.Data;

@Data
public class FoundTemplateDTO {
    private Long id;
    private String template_name;
    private String observation_period;
    private byte inspections_frequency;
}
