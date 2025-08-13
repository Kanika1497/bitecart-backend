package in.Kanika.foodorder.util;

import io.jsonwebtoken.Claims;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.core.internal.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    @Value("${jwt.secret.key}")
    private String SECRET_KEY;


    public String generateToken(UserDetails userDetails){
        Map<String,Object> claims=new HashMap<>();
        return createToken(claims,userDetails.getUsername());
    }

    private String createToken(Map<String,Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .subject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*10))
                .signWith(SignatureAlgorithm.HS256,SECRET_KEY)
                .compact();

    }

    public String extractUsername(String token){
        return extractClaims(token, Claims::getSubject);
    }
    public Date extractExpiration(String token){
        return extractClaims(token,Claims::getExpiration);
    }

    public <T> T extractClaims(String token , Function<Claims,T> claimResolver){
        final Claims claims=extractAllClaims(token);
        return claimResolver.apply(claims);
    }
    private Claims extractAllClaims(String token){
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token ,UserDetails userDetails){
        final String username =extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
