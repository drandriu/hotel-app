package com.example.hotel.hotelapp.services.auth;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    // Reemplazar con una clave secreta adecuada y más segura
    private static final String SECRET_KEY = "clave-super-secreta-para-jwt-con-muchos-caracteres";

    // Generación de la clave secreta para firmar el JWT
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes()); // Para generar una clave de firma
    }

    // Generación del token JWT
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername()) // Configura el nombre de usuario como "subject"
                .setIssuedAt(new Date(System.currentTimeMillis())) // Fecha de emisión
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // Tiempo de expiración (1 hora)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // Firma usando la clave secreta y algoritmo HS256
                .compact(); // Genera el token compacto
    }

    // Extraer el nombre de usuario del token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject); // Extrae el "subject", que es el username
    }

    // Validar que el token es válido comparando con el usuario
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token); // Extrae el nombre de usuario del token
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token)); // Verifica si el usuario es correcto y si el token no ha expirado
    }

    // Verificar si el token ha expirado
    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date()); // Verifica la fecha de expiración del token
    }

    // Método genérico para extraer reclamaciones (claims) del token
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parser() // Usamos parserBuilder() para crear el parser
                .setSigningKey(getSigningKey()) // Establecemos la clave para verificar la firma
                .build()
                .parseClaimsJws(token) // Decodificamos el token
                .getBody(); // Extraemos el cuerpo de las reclamaciones
        return claimsResolver.apply(claims); // Aplicamos el extractor de reclamaciones (por ejemplo, username, expiration)
    }
}