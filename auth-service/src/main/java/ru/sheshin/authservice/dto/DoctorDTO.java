package ru.sheshin.authservice.dto;

import lombok.Data;

@Data
public class DoctorDTO {
    private Long id;
    private String firstName;
    private String patronymic;
    private String lastName;
    private String specialty;
    private String token;
}
