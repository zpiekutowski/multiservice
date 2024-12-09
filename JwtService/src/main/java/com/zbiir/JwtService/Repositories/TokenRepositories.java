package com.zbiir.JwtService.Repositories;

import com.zbiir.JwtService.DTO.UserDTO;
import com.zbiir.JwtService.Model.TokenRepo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepositories extends JpaRepository<TokenRepo,Integer> {

    public Optional<TokenRepo> findByToken(String token);
    public Optional<TokenRepo> findByUsername(String username);

}
