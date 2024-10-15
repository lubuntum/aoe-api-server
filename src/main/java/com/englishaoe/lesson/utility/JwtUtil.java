package com.englishaoe.lesson.utility;

import com.englishaoe.lesson.config.AppConfig;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    public final Environment env;
    @Autowired
    public JwtUtil(Environment env){
        this.env = env;
    }
    public String generateToken(String data){
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, data);
    }
    private String createToken(Map<String, Object> claims, String subject){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*60*10))
                .signWith(SignatureAlgorithm.HS256, env.getProperty(AppConfig.SECRET_KEY_PROPERTY).getBytes())
                .compact();
    }
    public boolean validateToken(String token, String username){
        final String extractedUsername = extractSubject(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }
    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }
    public Date extractExpiration(String token){
        return Jwts.parser().setSigningKey(env.getProperty(AppConfig.SECRET_KEY_PROPERTY).getBytes()).parseClaimsJws(token).getBody().getExpiration();
    }
    public String extractSubject(String token){
        return Jwts.parser().setSigningKey(env.getProperty(AppConfig.SECRET_KEY_PROPERTY).getBytes(StandardCharsets.UTF_8)).parseClaimsJws(token).getBody().getSubject();
    }
}
