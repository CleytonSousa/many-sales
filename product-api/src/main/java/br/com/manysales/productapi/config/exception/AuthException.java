package br.com.manysales.productapi.config.exception;

public class AuthException extends RuntimeException {

    public AuthException(String message){
        super(message);
    }
}
