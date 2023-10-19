package com.joe.dating.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.joe.dating.domain.user.models.Gender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JwtService {
    private final String issuer = "dating-api";
    private final String secretKey;
    private final int expiryInDays;
    private final String frendaiSecretKey;

    public JwtService(@Value("${security.jwt.secret}") String secretKey,
                      @Value("${security.jwt.expiryInDays}") int expiryInDays,
                      @Value("${security.frendai.jwt.secret}") String frendaiSecretKey) {
        this.secretKey = secretKey;
        this.expiryInDays = expiryInDays;
        this.frendaiSecretKey = frendaiSecretKey;
    }

    public String createJwt(
                    Long userId,
                    String username,
                    boolean isPaid,
                    int completionStatus,
                    int siteId,
                    Gender gender,
                    Gender genderSeeking
    ) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            return JWT.create()
                    .withIssuer(issuer)
                    .withSubject(userId.toString())
                    .withClaim("username", username)
                    .withClaim("isPaid", isPaid)
                    .withClaim("completionStatus", completionStatus)
                    .withClaim("siteId", siteId)
                    .withClaim("gender", gender.name())
                    .withClaim("genderSeeking", genderSeeking.name())
                    .withExpiresAt(getExpireDate())
                    .sign(algorithm);
        } catch (UnsupportedEncodingException exception){
            throw new JWTCreationException("Could not create JWT token", exception);
        }
    }

    public DecodedJWT verifyJwt(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(issuer)
                    .build(); 
            return verifier.verify(token);
        } catch (UnsupportedEncodingException exception){
            throw new JWTVerificationException("Could not decode JWT token", exception);
        }
    }

    public String createFrendAIJwt(
            Long userId,
            String username,
            boolean isPaid,
            Gender gender,
            Gender genderSeeking
    ) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(frendaiSecretKey);
            return JWT.create()
                    .withIssuer("uglyschmucks")
                    .withSubject(userId.toString())
                    .withClaim("userId", userId)
                    .withClaim("username", username)
                    .withClaim("isPaid", isPaid)
                    .withClaim("gender", gender.name())
                    .withClaim("genderSeeking", genderSeeking.name())
                    .withClaim("expiry", getExpireDate())
                    .sign(algorithm);
        } catch (UnsupportedEncodingException exception){
            throw new JWTCreationException("Could not create JWT token", exception);
        }
    }

    private Date getExpireDate() {
        LocalDateTime localDateTime = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().plusDays(expiryInDays);
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
