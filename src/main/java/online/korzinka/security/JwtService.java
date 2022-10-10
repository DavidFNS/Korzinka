package online.korzinka.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import online.korzinka.DateUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtService {
    @Value("${spring.security.key}")
    private String key;

    public String generateToken(String user_id){
        return Jwts.builder()
                .claim("sub",user_id)
                .claim("exp", DateUtil.OneDay())
                .claim("iat", new Date())
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    public Object getClaim(String token, String name){
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody()
                .get(name);
    }

    public boolean validateToken(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
        return claims.getExpiration().after(new Date()) && claims.getSubject() != null;
    }
}
