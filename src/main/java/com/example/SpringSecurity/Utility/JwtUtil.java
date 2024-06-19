// JwtUtil.java
package com.example.SpringSecurity.Utility;

import com.example.SpringSecurity.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration.ms}")
    private int jwtExpirationMs;

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("username", user.getUserName());
        claims.put("email", user.getEmail_id());
        claims.put("isActive", user.isActive());
        claims.put("user_type", user.getUser_type());
        claims.put("loginCount", user.getLoginCount());
//        claims.put("created", user.getCreated());
//        claims.put("updated", user.getUpdated());

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUserName())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Method to get user details from the token
    public User getUserFromToken(String token) {
        Claims claims = parseToken(token);

        User user = new User();
        user.setId(((Number) claims.get("id")).longValue());
        user.setUserName(claims.get("username", String.class));
        user.setEmail_id(claims.get("email", String.class));
        user.setActive(claims.get("isActive", Boolean.class));
        user.setUser_type(claims.get("user_type", String.class));
        user.setLoginCount(claims.get("loginCount", Integer.class));
//        user.setCreated(claims.get("created", LocalDateTime.class));
//        user.setUpdated(claims.get("updated", LocalDateTime.class));

        return user;
    }
}
