package com.joe.dating.domain.user;

import com.joe.dating.common.Util;
import com.joe.dating.domain.user.models.CompletionStatus;
import com.joe.dating.domain.user.models.EmailSubscription;
import com.joe.dating.domain.user.models.Gender;
import com.joe.dating.domain.user.models.Profile;
import com.joe.dating.domain.validaiton.ValidationException;
import com.joe.dating.email_verification.EmailVerificationService;
import com.joe.dating.security.AuthContext;
import com.joe.dating.security.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
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
            @Value("${email.validation.enable}") boolean enableEmailVerification
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
        user.setPassword(Util.md5(password));
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

    public User completeUserJoin(Long userId, User joinUser) {
        User existingUser = findOne(userId);
        existingUser.setProfile(joinUser.getProfile());
        existingUser.setBirthDate(joinUser.getBirthDate());
        existingUser.setCompletionStatus(CompletionStatus.COMPLETE);
        return userRepository.save(existingUser);
    }

    public User updateProfile(Long userId, Profile profile) {
        User existingUser = findOne(userId);
        existingUser.setProfile(profile);
        return userRepository.save(existingUser);
    }

    public User updateEmailSubscription(Long userId, EmailSubscription emailSubscription) {
        User existingUser = findOne(userId);
        existingUser.setEmailSubscription(emailSubscription);
        return userRepository.save(existingUser);
    }

    public void delete(Long userId) {
        logger.info("Deleting user {}", userId);
        User user = findOne(userId);
        user.setIsDeleted(true);
        userRepository.save(user);
    }

    public Page<User> searchProfiles(AuthContext authContext, Pageable pageable, int ageFrom, int ageTo, String countryId, String regionId, String cityId) {
        List<Specification<User>> specifications = new ArrayList<>();
        specifications.add(UserSpecification.hasCommonFields());
        specifications.add(UserSpecification.ageFrom(ageFrom));
        specifications.add(UserSpecification.ageTo(ageTo));
        specifications.add(UserSpecification.gender(authContext.getGenderSeeking().name()));
        specifications.add(UserSpecification.genderSeeking(authContext.getGender().name()));

        if(countryId != null && countryId.length() > 0) {
            specifications.add(UserSpecification.hasCountry(countryId));
        }

        if(regionId != null && regionId.length() > 0) {
            specifications.add(UserSpecification.hasRegion(regionId));
        }

        if(cityId != null && cityId.length() > 0) {
            specifications.add(UserSpecification.hasCity(cityId));
        }

        Specification<User> result = specifications.get(0);
        for (int i = 1; i < specifications.size(); i++) {
            result = Specifications.where(result).and(specifications.get(i));
        }

        return userRepository.findAll(result, pageable);
    }

    private Profile userToProfile(User user) {
        return user.getProfile();
    }

}
