package ru.sheshin.authservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sheshin.authservice.dto.AuthCheckDTO;
import ru.sheshin.authservice.dto.DoctorDTO;
import ru.sheshin.authservice.service.AuthService;

import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {
    private static final AuthService SERVICE = new AuthService();

    @PostMapping("/auth")
    public ResponseEntity<DoctorDTO> auth(@RequestBody Map<String, String> request) {
        var doctor = SERVICE.authDoctor(request.get("login"), request.get("password"));

        if(doctor != null) {
            return ResponseEntity.ok()
                    .body(doctor);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    @PostMapping("/checkAuth")
    public ResponseEntity<AuthCheckDTO> checkAuth(@RequestBody Map<String, String> request) {
        return ResponseEntity
                .ok()
                .body(SERVICE.checkAuth(request.get("token")));
    }
}
