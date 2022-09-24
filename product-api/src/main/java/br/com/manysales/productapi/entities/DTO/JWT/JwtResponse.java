package br.com.manysales.productapi.entities.DTO.JWT;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class JwtResponse {
   private JwtDTO userAuth;
    private String iat;
    private String exp;
    public static JwtResponse getUser(Claims jwtClaims){
        try{
            return new ObjectMapper().convertValue(jwtClaims, JwtResponse.class);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
