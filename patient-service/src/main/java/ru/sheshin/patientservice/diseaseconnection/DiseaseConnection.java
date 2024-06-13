package ru.sheshin.patientservice.diseaseconnection;

import ru.sheshin.patientservice.json.JsonConverter;
import ru.sheshin.patientservice.json.jsonbean.DiseaseJson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class DiseaseConnection {
    private static final JsonConverter JSON_CONVERTER = new JsonConverter();
    public DiseaseJson getDiseaseById(Long diseaseId) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8085/diseases/" + diseaseId))
                    .build();

            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());

            return JSON_CONVERTER.convertJsonStringToBean(response.body());
        } catch (Exception e) {
            return null;
        }
    }
}
