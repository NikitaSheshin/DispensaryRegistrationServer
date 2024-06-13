package ru.sheshin.templateservice.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateObservationDateDto {
    private Long patientId;
    private Long doctorId;
    private LocalDate nextObservationDate;
}
