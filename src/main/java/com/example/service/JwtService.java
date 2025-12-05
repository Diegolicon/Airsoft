package com.example.service;

import com.example.model.Perfil;

public interface JwtService {
    public String generateJwt(String usuario, Perfil perfil);
}
