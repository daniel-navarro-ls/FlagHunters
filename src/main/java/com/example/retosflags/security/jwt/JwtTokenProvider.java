package com.example.retosflags.security.jwt;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtTokenProvider {
    
    private static final Logger LOG = LoggerFactory.getLogger(JwtTokenProvider.class);
    
    @Value("${jwt.secret}")
    private String jwtSecret;
    
    private static long JWT_EXPIRATION_IN_MS = 5400000;
    private static Long REFRESH_TOKEN_EXPIRATION_MSEC = 10800000L;
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token);
            return true;
        } catch (SecurityException ex) {
            LOG.debug("Invalid JWT Signature");
        } catch (MalformedJwtException ex) {
            LOG.debug("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            LOG.debug("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            LOG.debug("Unsupported JWT exception");
        } catch (IllegalArgumentException ex) {
            LOG.debug("JWT claims string is empty");
        }
        return false;
    }

    public Token generateToken(UserDetails user) {
        Claims claims = Jwts.claims()
                .subject(user.getUsername())
                .add("auth", user.getAuthorities().stream()
                        .map(authority -> new SimpleGrantedAuthority("ROLE_" + authority.getAuthority()))
                        .collect(Collectors.toList()))
                .build();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION_IN_MS);

        String token = Jwts.builder()
                .claims(claims)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getSigningKey())
                .compact();

        return new Token(Token.TokenType.ACCESS, token, 
                        Long.valueOf(JWT_EXPIRATION_IN_MS),
                        LocalDateTime.ofInstant(expiryDate.toInstant(), ZoneId.systemDefault()));
    }

    public Token generateRefreshToken(UserDetails user) {
        Claims claims = Jwts.claims()
                .subject(user.getUsername())
                .add("auth", user.getAuthorities().stream()
                        .map(authority -> new SimpleGrantedAuthority("ROLE_" + authority.getAuthority()))
                        .collect(Collectors.toList()))
                .build();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + REFRESH_TOKEN_EXPIRATION_MSEC);

        String token = Jwts.builder()
                .claims(claims)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getSigningKey())
                .compact();

        return new Token(Token.TokenType.REFRESH, token, 
                        REFRESH_TOKEN_EXPIRATION_MSEC,
                        LocalDateTime.ofInstant(expiryDate.toInstant(), ZoneId.systemDefault()));
    }
}