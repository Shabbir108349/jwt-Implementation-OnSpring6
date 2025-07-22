package com.shabbir.Jwt_Impl.Utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtils {

    private SecretKey getSignKey(){
        String SECRET_KEY = "TaK+HaV^uvCHEFsEVfypW#7g9^k*Z8$V";
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String generateToke(String username){
        Map<String , Object> claims = new HashMap<>();
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+ 1000*60))
                .and()
                .header().empty().add("type","jwt")
                .and()
                .signWith(getSignKey())
                .compact();
    }

    public String extractUsername(String token){
        return getAllClaims(token).getSubject();
    }

    private Claims getAllClaims(String token){
        return Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean isValidToken(String token){
        Date expiration = getAllClaims(token).getExpiration();
        Date currentTime = new Date();
        return !expiration.before(currentTime);

    }

}
