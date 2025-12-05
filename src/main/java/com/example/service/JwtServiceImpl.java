package com.example.service;

import java.io.InputStream;
import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger; // 👈 Import para o Logger

import com.example.model.Perfil;
import io.smallrye.jwt.build.Jwt;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class JwtServiceImpl implements JwtService {
    private static final Duration EXPIRATION_TIME = Duration.ofHours(24);

    @Override
    public String generateJwt(String login, Perfil perfil) {

        // data
        Instant expiryDate = Instant.now().plus(EXPIRATION_TIME);

        // papeis (perfil)
        Set<String> roles = new HashSet<String>();
        roles.add(perfil.name());

        // gerando o token
        return Jwt.issuer("unitins-jwt")
                .subject(login)
                .groups(roles)
                .expiresAt(expiryDate)
                .sign();

    }
}
