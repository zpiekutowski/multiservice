package com.zbiir.JwtService.Exception;

public class TokenNotValidException extends RuntimeException{
    public TokenNotValidException(String mesage){
        super(mesage);
    }
}
