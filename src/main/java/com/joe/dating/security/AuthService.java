package com.joe.dating.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.joe.dating.common.Util;
import com.joe.dating.domain.user.User;
import com.joe.dating.domain.user.UserRepository;
import com.joe.dating.domain.user.UserService;
import com.joe.dating.domain.user.models.Gender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

@Component
public class AuthService {

    private final Logger logger = LoggerFactory.getLogger(AuthService.class);
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public AuthService(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    public AuthContext createAuthContext(String email, String rawPassword) {
        User user = userRepository.findByEmailAndPassword(email, Util.md5(rawPassword));
        if(user == null) {
            logger.info("Login failure. email={}", email);
            throw new IllegalArgumentException("Invalid login");
        }
        if(user.isDeleted()) {
            logger.info("Attempted login to deleted account. email={}", email);
            throw new IllegalArgumentException("Account deleted");
        }

        return createAuthContext(
                user.getId(),
                user.isPaid(),
                user.getUsername(),
                user.getCompletionStatus(),
                user.getSiteId(),
                user.getGender(),
                user.getGenderSeeking()
        );

    }

    public AuthContext createAuthContext(Long userId,
                                         boolean isPaid,
                                         String username,
                                         int completionStatus,
                                         int siteId,
                                         Gender gender,
                                         Gender genderSeeking) {

        String token = jwtService.createJwt(
                userId,
                username,
                isPaid,
                completionStatus,
                siteId,
                gender,
                genderSeeking);

        AuthContext authContext = new AuthContext();

        authContext.setToken(token);
        authContext.setUsername(username);
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
        authContext.setUsername(jwt.getClaim("username").asString());

        return authContext;
    }

    private String getHashedPassword(String password) {
        return DigestUtils.md5Digest(password.getBytes()).toString();
    }
}
