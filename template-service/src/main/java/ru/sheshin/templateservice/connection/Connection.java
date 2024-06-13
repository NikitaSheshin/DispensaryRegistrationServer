package ru.sheshin.templateservice.connection;

import ru.sheshin.templateservice.json.JsonConverter;
import ru.sheshin.templateservice.json.jsonbean.DiseaseJson;
import ru.sheshin.templateservice.json.jsonbean.PatientJson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Connection {
    private static final JsonConverter JSON_CONVERTER = new JsonConverter();
    public DiseaseJson getDiseaseById(Long diseaseId) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8085/diseases/" + diseaseId))
                    .build();

            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());

            return JSON_CONVERTER.convertDiseaseJsonStringToBean(response.body());
        } catch (Exception e) {
            return null;
        }
    }

    public PatientJson getPatientById(Long patientId) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8086/patients/" + patientId))
                    .build();

            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());

            return JSON_CONVERTER.convertPatientJsonStringToBean(response.body());
        } catch (Exception e) {
            return null;
        }
    }
}
