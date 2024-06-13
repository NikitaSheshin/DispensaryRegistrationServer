package ru.sheshin.patientservice.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class PatientDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private LocalDate birthday;
    private String address;
    private String phoneNumber;
    private boolean isObserved;
    private String passportNumber;
    private String passportSeries;
    private String snilsNumber;
    private String omsPolicy;
    private boolean gender;
    private Set<Long> diseases;
    private TemplatePatientDTO template;
}
