package com.zbiir.JwtService.Controler;

import com.zbiir.JwtService.DTO.JsonToken;
import com.zbiir.JwtService.DTO.UserDTO;
import com.zbiir.JwtService.Service.JwtService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/token")
public class JwtControler {
    @Autowired
    JwtService jwtService;

    @PostMapping("/register")

    public JsonToken getToken(@Valid @RequestBody UserDTO userDTO){
       // System.out.println("obsluga getToken");
        return jwtService.getJwtToken(userDTO);

    }
    @PostMapping("/validate")
    public UserDTO validateToken(@RequestBody JsonToken token){

        return jwtService.tokenValidation(token.getToken());

    }
    @PostMapping("/invalidate")
    public ResponseEntity makeTokenInvalid(@RequestBody JsonToken token){
        jwtService.makeTokenInvalid(token);
        return ResponseEntity.ok().build();
    }




}