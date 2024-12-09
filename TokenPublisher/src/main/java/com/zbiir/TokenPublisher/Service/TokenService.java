package com.zbiir.TokenPublisher.Service;

import com.zbiir.TokenPublisher.DTO.TokenDTO;
import com.zbiir.TokenPublisher.DTO.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TokenService {

    String uri = "http://localhost:8081/token/register";

    public String registerUser() {
        RestTemplate restTemplate = new RestTemplate();
        User user = new User("u2","admin");

        //ResponseEntity<TokenDTO> response = restTemplate.postForEntity(uri, user, TokenDTO.class);



        System.out.println(response.getStatusCode());

        return response.getBody().getToken();
    }
}
