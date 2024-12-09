package com.zbiir.TokenPublisher.Controler;

import com.zbiir.TokenPublisher.Service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenControler {

    @Autowired
    TokenService tokenService;

    @GetMapping("/hello")
    public String helloWorld(){
        return "Hello world";
    }

    @GetMapping("/get_token")
    public String getToken(){

        return tokenService.registerUser();

    }




}
