package com.learn.bean.scopes.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTokenService {

    public static void main(String[] args) {
        long duration = 1000 * 60 * 30; // 30 minutes in milliseconds
        String token = generateToken(duration, generateSecretKey());
        System.out.println("Token: " + token);
    }

    public static String generateToken(long duration, Key secretKey) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("name", "Java Learnerss");  // Name
        claims.put("role", "USER");   // Custom claim

        return Jwts.builder()
                .setClaims(claims)
                .setSubject("javalearnerss")
                .setIssuedAt((new Date(System.currentTimeMillis())))
                .setExpiration(new Date(System.currentTimeMillis() + duration))
                .signWith(secretKey)
                .setHeaderParam("typ", "JWT")
                .compact();
    }

    public static Key generateSecretKey() {
        Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        // Display the security key
        byte[] keyBytes = secretKey.getEncoded();
        String base64Key = Encoders.BASE64.encode(keyBytes);
        System.out.println("Generated key: " + base64Key);
        return secretKey;
    }
}
