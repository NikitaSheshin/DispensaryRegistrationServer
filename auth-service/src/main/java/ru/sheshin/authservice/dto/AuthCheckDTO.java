package ru.sheshin.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthCheckDTO {
    private boolean isValid;
    private String token;
}
