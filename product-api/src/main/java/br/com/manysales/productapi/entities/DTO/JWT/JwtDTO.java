package br.com.manysales.productapi.entities.DTO.JWT;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class JwtDTO {
    private Integer id;
    private String name;
    private String email;
}
