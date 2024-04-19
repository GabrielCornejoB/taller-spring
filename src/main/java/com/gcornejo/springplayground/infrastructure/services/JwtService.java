package com.gcornejo.springplayground.infrastructure.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;

@Service
public class JwtService {

    private static final String SECRET_KEY = "1212121212121212121212121212121212121212121212121212121212121212";

    public String getTokenFromUser(UserDetails user) {
        int DAY_IN_MILLIS = 1000 * 60 * 24;
        return Jwts.builder()
                .setClaims(new HashMap<>())
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + DAY_IN_MILLIS))
                .signWith(this.getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getEmailFromToken(String token) {
        return this.getClaimsObject(token).getSubject();
    }

    public boolean isTokenValid(String token, UserDetails user) {
        String email = this.getEmailFromToken(token);
        Date expirationDate = this.getClaimsObject(token).getExpiration();

        return (email.equals(user.getUsername()) && !expirationDate.before(new Date()));
    }

    private Claims getClaimsObject(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(this.getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(SECRET_KEY));
    }

}
