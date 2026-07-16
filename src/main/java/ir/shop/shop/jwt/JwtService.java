package ir.shop.shop.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import ir.shop.shop.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.query.KeysetScrollDelegate;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

@Component
public class JwtService {
    @Value("${jwt.secret}")
    private String sec;

    @Value("${jwt.expire}")
    private Long exp;

    private SecretKey getKeySign() {
        return Keys.hmacShaKeyFor(sec.getBytes(StandardCharsets.UTF_8));
    }

    public String tokenGenerate(User user) {
        return Jwts.builder().subject(user.getEmail()).claim(
                "role",
                        user.getRole().getName()
        ) .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + exp))
                .signWith(getKeySign())
                .compact();
    }
    private Claims parseClaims(String token) {
        return Jwts.parser().verifyWith(getKeySign()) .build().parseSignedClaims(token).getPayload();
    }

    public String extractEmail (String token) {
        return parseClaims(token).getSubject();
    }

    public String extractRole (String token) {
        return parseClaims(token).get("role",String.class);
    }
    public List<SimpleGrantedAuthority> extractAuth (String token) {
        String role = extractRole(token);

        if(role == null) {
            return List.of();
        }

        return List.of(new SimpleGrantedAuthority(role));
    }
    public boolean isTokenExp(String token) {
        return parseClaims(token).getExpiration().before(new Date());
    }
    public boolean tokenValidate(String token, UserDetails userDetails) {
        return extractEmail(token).equals(userDetails.getUsername()) && !isTokenExp(token);
    }
}
