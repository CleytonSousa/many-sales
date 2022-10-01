package br.com.manysales.productapi.config.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class SucessResponse {

    private Integer status;
    private String message;

    public static SucessResponse create(String message){
        return SucessResponse.builder()
                .message(message)
                .status(HttpStatus.OK.value()).build();
    }
}
