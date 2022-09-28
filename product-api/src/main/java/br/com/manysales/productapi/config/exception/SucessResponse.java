package br.com.manysales.productapi.config.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
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
