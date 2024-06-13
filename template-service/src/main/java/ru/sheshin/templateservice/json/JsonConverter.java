package ru.sheshin.templateservice.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ru.sheshin.templateservice.json.jsonbean.DiseaseJson;
import ru.sheshin.templateservice.json.jsonbean.PatientJson;

public class JsonConverter {
    private final ObjectMapper objectMapper;

    public JsonConverter() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }
    public DiseaseJson convertDiseaseJsonStringToBean(String jsonSting) {
        try {
            return objectMapper.readValue(jsonSting, DiseaseJson.class);
        } catch (Exception e) {
            return null;
        }
    }

    public PatientJson convertPatientJsonStringToBean(String jsonSting) {
        try {
            return objectMapper.readValue(jsonSting, PatientJson.class);
        } catch (Exception e) {
            return null;
        }
    }
}
