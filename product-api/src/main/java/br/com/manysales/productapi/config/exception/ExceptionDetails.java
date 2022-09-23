package br.com.manysales.productapi.config.exception;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ExceptionDetails {
    private int status;
    private String message;


}
