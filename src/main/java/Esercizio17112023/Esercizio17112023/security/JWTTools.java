package Esercizio17112023.Esercizio17112023.security;

import Esercizio17112023.Esercizio17112023.entities.User;
import Esercizio17112023.Esercizio17112023.exceptions.Unauthorized;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTTools {
    @Value("${spring.jwt.secret}")
    private String secret;
    public String creaToken(User u){
       return Jwts.builder()
                .setSubject(String.valueOf(u.getId()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+(1000*60*60*24*7)))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes())).compact();
    }

    public void verificaToken(String token){
        try{
            Jwts.parserBuilder().setSigningKey(secret.getBytes()).build().parse(token);

        }catch(Exception e){
            throw new Unauthorized("Token non valido");
        }
    }
    public String getId(String token){
       return Jwts.parserBuilder().setSigningKey(secret.getBytes()).build().parseClaimsJws(token).getBody().getSubject();
    }
}
