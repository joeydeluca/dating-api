package com.joe.dating.domain.user;

import com.joe.dating.domain.user.models.CompletionStatus;
import com.joe.dating.domain.user.models.Gender;
import com.joe.dating.domain.user.models.Profile;
import com.joe.dating.domain.validaiton.ValidationException;
import com.joe.dating.email_verification.EmailVerificationService;
import com.joe.dating.security.AuthContext;
import com.joe.dating.security.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.util.List;

/**
 * Created by Joe Deluca on 11/21/2016.
 */
@Component
public class UserService {

    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    private UserRepository userRepository;
    private EmailVerificationService emailVerificationService;
    private AuthService authService;
    private boolean enableEmailVerification;

    public UserService(
            UserRepository userRepository,
            EmailVerificationService emailVerificationService,
            AuthService authService,
            @Value("${email.validation.enable-email-verification}") boolean enableEmailVerification
    ) {
        this.userRepository = userRepository;
        this.emailVerificationService = emailVerificationService;
        this.authService = authService;
        this.enableEmailVerification = enableEmailVerification;
    }

    public User findOne(Long id) {
        User user = userRepository.findOne(id);
        if(user == null) {
            throw new IllegalArgumentException("User id does not exist");
        }
        return user;
    }

    public List<User> search(int ageFrom, int ageTo, String countryId, String regionId, String cityId) {
        return userRepository.findAll();
    }

    public AuthContext create(String email, String username, String password, Gender gender, Gender genderSeeking) {
        if(userRepository.findByEmail(email).size() > 0) {
            throw new ValidationException("email", "Email address must be unique");
        }
        if(userRepository.findByUsername(username).size() > 0) {
            throw new ValidationException("username", "Username must be unique");
        }
        try {
            if (enableEmailVerification && !emailVerificationService.verify(email)) {
                throw new ValidationException("email", "Cannot send to the email provided");
            }
        } catch(Exception e) {
            logger.error("Error when verifying email. exception={}", e.getMessage());
        }

        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(getHashedPassword(password));
        user.setGender(gender);
        user.setGenderSeeking(genderSeeking);
        user.setCompletionStatus(CompletionStatus.INCOMPLETE);

        User createdUser = userRepository.save(user);

        return authService.createAuthContext(
                createdUser.getId(),
                createdUser.isPaid(),
                createdUser.getCompletionStatus(),
                createdUser.getSiteId(),
                createdUser.getGender(),
                createdUser.getGenderSeeking()
                );
    }

    public User updateProfile(Long userId, Profile profile) {
        User user = findOne(userId);
        user.setProfile(profile);
        return userRepository.save(user);
    }

    private String getHashedPassword(String password) {
        return DigestUtils.md5Digest(password.getBytes()).toString();
    }
}
