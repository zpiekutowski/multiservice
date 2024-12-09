package com.zbiir.JwtService.Advicer;

import com.zbiir.JwtService.Exception.TokenNotValidException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionAdviser {

    @ExceptionHandler(TokenNotValidException.class)
    public ResponseEntity<?> handlerTokenNotValidExceptin(TokenNotValidException exp){
        Map<String,String> errorMap = new HashMap<>();
        errorMap.put("message", exp.getMessage());
        return ResponseEntity.badRequest().body(errorMap);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handlerValidationUserDTO(MethodArgumentNotValidException exp){
        Map<String,String> errorMap = new HashMap<>();
        errorMap.put("message","user registration error");
        exp.getBindingResult().getAllErrors().forEach(error->
                errorMap.put(((FieldError)error).getField(),error.getDefaultMessage()));

        return ResponseEntity.badRequest().body(errorMap);
    }
}
