package com.example.joinpratice.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.sql.Date;

public class JwtTokenUtil {

    public static String createToken(String username, String key, long expireTimes) {
        Claims claims = Jwts.claims();
        claims.put("username", username);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTimes))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }
}
