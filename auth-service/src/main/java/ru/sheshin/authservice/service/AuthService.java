package ru.sheshin.authservice.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.mapstruct.factory.Mappers;
import ru.sheshin.authservice.dto.AuthCheckDTO;
import ru.sheshin.authservice.dto.DoctorDTO;
import ru.sheshin.authservice.mapper.DoctorMapper;
import ru.sheshin.authservice.repository.AuthRepository;

import javax.crypto.SecretKey;
import java.util.Date;

public class AuthService {
    private static final AuthRepository REPOSITORY = new AuthRepository();
    private static final DoctorMapper MAPPER = Mappers.getMapper(DoctorMapper.class);
    private final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    public DoctorDTO authDoctor(final String login, final String password) {
        var doctorEntity = REPOSITORY.authDoctor(login, password);

        if(doctorEntity == null) {
            return null;
        }
        var doctorDto = MAPPER.entityToDTO(
                doctorEntity
        );

        doctorDto.setToken(generateJWT(login, password));

        return doctorDto;
    }

    public AuthCheckDTO checkAuth(final String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            Date expirationDate = claims.getExpiration();
            Date now = new Date();
            if (expirationDate.before(now)) {
                return new AuthCheckDTO(false, "");
            }

            String username = (String) claims.get("login");
            String password = (String) claims.get("password");

            var doctorEntity = REPOSITORY.authDoctor(username, password);

            if(doctorEntity == null) {
                return new AuthCheckDTO(false, "");
            } else {
                return new AuthCheckDTO(true, token);
            }
        } catch (JwtException e) {
            return null;
        }
    }

    private String generateJWT(String username, String password) {
        long expirationTime = 3600000;
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationTime);

        return Jwts.builder()
                .claim("login", username)
                .claim("password", password)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SECRET_KEY)
                .compact();
    }
}
