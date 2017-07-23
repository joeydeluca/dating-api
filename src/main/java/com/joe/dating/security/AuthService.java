package com.joe.dating.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.joe.dating.domain.user.models.Gender;
import org.springframework.stereotype.Component;

@Component
public class AuthService {

    private JwtService jwtService;

    public AuthService(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public AuthContext createAuthContext(Long userId,
                                         boolean isPaid,
                                         int completionStatus,
                                         int siteId,
                                         Gender gender,
                                         Gender genderSeeking) {

        String token = jwtService.createJwt(
                userId,
                isPaid,
                completionStatus,
                siteId,
                gender,
                genderSeeking);

        AuthContext authContext = new AuthContext();
        authContext.setToken(token);
        authContext.setUserId(userId);
        authContext.setPaid(isPaid);
        authContext.setCompletionStatus(completionStatus);
        authContext.setGender(gender);
        authContext.setGenderSeeking(genderSeeking);
        authContext.setSiteId(siteId);

        return authContext;
    }

    public AuthContext verifyToken(String token) {
        DecodedJWT jwt = jwtService.verifyJwt(token);

        AuthContext authContext = new AuthContext();
        authContext.setUserId(Long.parseLong(jwt.getSubject()));
        if (
                jwt.getSubject() == null || jwt.getSubject().length() == 0 ||
                        jwt.getClaim("isPaid").isNull() ||
                        jwt.getClaim("completionStatus").isNull() ||
                        jwt.getClaim("gender").isNull() ||
                        jwt.getClaim("genderSeeking").isNull() ||
                        jwt.getClaim("siteId").isNull()
                ) {
            throw new JWTVerificationException("JWT is missing claims");
        }

        authContext.setUserId(Long.parseLong(jwt.getSubject()));
        authContext.setCompletionStatus(jwt.getClaim("completionStatus").asInt());
        authContext.setSiteId(jwt.getClaim("siteId").asInt());
        authContext.setGender(Gender.valueOf(jwt.getClaim("gender").asString()));
        authContext.setGenderSeeking(Gender.valueOf(jwt.getClaim("genderSeeking").asString()));
        authContext.setPaid(jwt.getClaim("isPaid").asBoolean());
        return authContext;
    }
}
