package br.com.manysales.productapi.services;

import br.com.manysales.productapi.config.exception.AuthException;
import br.com.manysales.productapi.entities.DTO.JWT.JwtResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class JwtService {

    private static final String EMPTY_SPACE = " ";
    private static final Integer TOKEN_INDEX = 1;

    @Value("${app-config.secrets.api-secret}")
    private String apiSecret;

    public void validateAuthorization(String token){
        String accessToken = extractToken(token);
        try{
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(apiSecret.getBytes()))
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();

            JwtResponse user = JwtResponse.getUser(claims);

            if(ObjectUtils.isEmpty(user) || ObjectUtils.isEmpty(user.getUserAuth().getId())){
                throw new AuthException("User not valid");
            }
        } catch (Exception e){
            e.printStackTrace();
            throw new AuthException("Error while trying process the acess token");
        }
    }

    private String extractToken(String token){
        if(ObjectUtils.isEmpty(token)){
            throw new AuthException("Token not present");
        }
        if(token.contains(EMPTY_SPACE)){
            token = token.split(" ")[TOKEN_INDEX];
        }

        return token;
    }
}
