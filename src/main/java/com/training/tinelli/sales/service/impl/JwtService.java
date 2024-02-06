package com.training.tinelli.sales.service.impl;

import com.training.tinelli.sales.domain.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.Date;

@Service
public class JwtService {
    @Value("${security.jwt.expiration}")
    private String expirationDate;
    @Value("${security.jwt.secret-key}")
    private String signatureKey;

    public String generateToken(User user) {

        /* Criar a data de expiração */
        long exp = Long.parseLong(expirationDate);
        LocalDateTime dateTimeExpiration = LocalDateTime.now().plusMinutes(exp);
        // Criar uma data de expiração global
        Instant instant = dateTimeExpiration.atZone(ZoneId.systemDefault()).toInstant();
        // Transforma para segundos
        Date date = Date.from(instant);

        /*
         * Assinar o token com a chave de assinatura e o algoritmo de cripto.
         * Inserir informações de payload no token
         * Definir a data de expiração
         */
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setExpiration(date)

                .signWith(SignatureAlgorithm.HS256, signatureKey)
                .compact();
    }

    /* Obtêm as informações de payload (body) do token */
    private Claims getClaims(String token) throws ExpiredJwtException {
        return Jwts
                .parser()
                .setSigningKey(signatureKey)
                .parseClaimsJws(token)
                .getBody();
    }

    /* Verificar se o token não expirou */
    public boolean isTokenValid(String token) {
        try {
            Claims claims = getClaims(token);
            Date expirationDate = claims.getExpiration();
            LocalDateTime dateTime = expirationDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

            return !LocalDateTime.now().isAfter(dateTime);
        } catch (Exception e) {
            return false;
        }
    }

    /* Obter do corpo do token o username do usuário */
    public String getUsername(String token) throws ExpiredJwtException {
        return getClaims(token).getSubject();
    }
}
