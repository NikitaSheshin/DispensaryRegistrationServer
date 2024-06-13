package ru.sheshin.templateservice.json.jsonbean;

import lombok.Data;
import ru.sheshin.templateservice.dto.DiseaseDTO;

import java.time.LocalDate;
import java.util.Set;

@Data
public class PatientJson {
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
    private Set<DiseaseDTO> diseases;
    private boolean gender;
}
