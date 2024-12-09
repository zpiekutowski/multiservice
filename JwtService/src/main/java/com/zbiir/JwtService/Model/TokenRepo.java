package com.zbiir.JwtService.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@Table(name = "jwt_token")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TokenRepo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String token;

    private boolean isValid;

    //@Column(name = "user_name")
    private String username;
}
