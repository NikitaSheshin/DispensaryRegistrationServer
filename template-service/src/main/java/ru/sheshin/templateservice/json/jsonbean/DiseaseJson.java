package ru.sheshin.templateservice.json.jsonbean;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DiseaseJson {
    private int id;
    @JsonProperty("icd_id")
    private String icdId;
    @JsonProperty("disease_name")
    private String diseaseName;
}
