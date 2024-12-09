package com.zbiir.JwtService.Service;

import com.zbiir.JwtService.DTO.JsonToken;
import com.zbiir.JwtService.DTO.UserDTO;
import com.zbiir.JwtService.Exception.TokenNotValidException;
import com.zbiir.JwtService.Model.TokenRepo;
import com.zbiir.JwtService.Repositories.TokenRepositories;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class JwtService {

    private final Logger logger = LoggerFactory.getLogger(JwtService.class);
    @Value("${security.key}")
    private String key;

    @Autowired
    private TokenRepositories tokenRepositories;

    public JsonToken getJwtToken(UserDTO userDTO) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("role", userDTO.getRole());

        String token =
                Jwts
                        .builder()
                        .claims(extraClaims)
                        .subject(userDTO.getUsername())
                        .issuedAt(new Date(System.currentTimeMillis()))
                        .expiration(new Date(System.currentTimeMillis() + 1000L * 60L * 2L))
                        .signWith(getKey())
                        .compact();

        Optional<TokenRepo> userTokenOptional = tokenRepositories.findByUsername(userDTO.getUsername());
        TokenRepo newTokenRepo = userTokenOptional.orElse(new TokenRepo(0, null, false, userDTO.getUsername()));
        newTokenRepo.setToken(token);
        newTokenRepo.setValid(true);
        tokenRepositories.save(newTokenRepo);
        return new JsonToken(token);
    }

    public UserDTO tokenValidation(String token) {

        Optional<TokenRepo> tokenRepoOptional = tokenRepositories.findByToken(token);
        if(tokenRepoOptional.isEmpty()||!tokenRepoOptional.get().isValid())
            throw new TokenNotValidException("Token not present or invalidated");

        Claims tokenClaims = extractClaims(token);
        return new UserDTO(tokenClaims.getSubject(),(String) tokenClaims.get("role"));

    }

    public void makeTokenInvalid(JsonToken token) {
        Optional<TokenRepo> tokenOptional = tokenRepositories.findByToken(token.getToken());
        TokenRepo tokenRepo = tokenOptional.orElseThrow(() -> new TokenNotValidException("Token not exist"));
        if (!tokenRepo.isValid())
            throw new TokenNotValidException("Token already invalidated");
        tokenRepo.setValid(false);
        tokenRepositories.save(tokenRepo);
    }


    private Claims extractClaims(String token) {

        try {
            return Jwts.parser()
                    .verifyWith(getKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            logger.info("Jwt Expired");
            throw new TokenNotValidException("Wrong token");
        }catch(SignatureException e){
            logger.info("Signature not valid");
            throw new TokenNotValidException("Wrong token");
        }catch(MalformedJwtException e){
            logger.info("Jwt not valid malformed");
            throw new TokenNotValidException("Wrong token");
        }catch(IllegalArgumentException e){
            logger.info("Jwt Empty");
            throw new TokenNotValidException("Wrong token");
        }catch(UnsupportedJwtException e){
            logger.info("Jwt Unsupported");
            throw new TokenNotValidException("Wrong token");
        }

       // return null;
    }


    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(key);
        return Keys.hmacShaKeyFor(keyBytes);

    }
}
