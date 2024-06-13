package ru.sheshin.patientservice.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.sheshin.patientservice.json.jsonbean.DiseaseJson;

public class JsonConverter {
    private final ObjectMapper objectMapper;

    public JsonConverter() {
        objectMapper = new ObjectMapper();
    }
    public DiseaseJson convertJsonStringToBean(String jsonSting) {
        try {
            return objectMapper.readValue(jsonSting, DiseaseJson.class);
        } catch (Exception e) {
            return null;
        }
    }
}
