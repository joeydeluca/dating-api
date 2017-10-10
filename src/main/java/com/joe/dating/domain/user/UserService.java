package com.joe.dating.domain.user;

import com.joe.dating.common.Util;
import com.joe.dating.domain.user.models.CompletionStatus;
import com.joe.dating.domain.user.models.EmailSubscription;
import com.joe.dating.domain.user.models.Gender;
import com.joe.dating.domain.user.models.Profile;
import com.joe.dating.domain.validaiton.ValidationException;
import com.joe.dating.email_verification.EmailVerificationService;
import com.joe.dating.rest.RecipientProfile;
import com.joe.dating.security.AuthContext;
import com.joe.dating.security.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.joe.dating.config.CacheConfig.PROFILE_SEARCH_CACHE;
import static com.joe.dating.config.CacheConfig.USER_BY_ID_CACHE;

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

    @Cacheable(cacheNames = USER_BY_ID_CACHE, key = "#id")
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
        if (enableEmailVerification && !emailVerificationService.verify(email)) {
            throw new ValidationException("email", "Cannot send to the email provided");
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

    @CacheEvict(cacheNames = USER_BY_ID_CACHE, key = "#userId")
    public User completeUserJoin(Long userId, User joinUser) {
        User existingUser = findOne(userId);
        existingUser.setProfile(joinUser.getProfile());
        existingUser.setBirthDate(joinUser.getBirthDate());
        existingUser.setCompletionStatus(CompletionStatus.COMPLETE);
        return userRepository.save(existingUser);
    }

    @CacheEvict(cacheNames = USER_BY_ID_CACHE, key = "#userId")
    public User updateProfile(Long userId, Profile profile) {
        User existingUser = findOne(userId);
        existingUser.setProfile(profile);
        return userRepository.save(existingUser);
    }

    @CacheEvict(cacheNames = USER_BY_ID_CACHE, key = "#userId")
    public User updateEmailSubscription(Long userId, EmailSubscription emailSubscription) {
        User existingUser = findOne(userId);
        existingUser.setEmailSubscription(emailSubscription);
        return userRepository.save(existingUser);
    }

    @CacheEvict(cacheNames = USER_BY_ID_CACHE, key = "#userId")
    public void delete(Long userId) {
        logger.info("Deleting user {}", userId);
        User user = findOne(userId);
        user.setIsDeleted(true);
        userRepository.save(user);
    }

    @Cacheable(PROFILE_SEARCH_CACHE)
    public Page<RecipientProfile> searchProfiles(Gender gender, Gender genderSeeking, int pageNumber, int pageSize, int ageFrom, int ageTo, String countryId, String regionId, String cityId) {
        List<Specification<User>> specifications = new ArrayList<>();
        specifications.add(UserSpecification.hasCommonFields());
        specifications.add(UserSpecification.ageFrom(ageFrom));
        specifications.add(UserSpecification.ageTo(ageTo));
        specifications.add(UserSpecification.gender(genderSeeking.name()));
        specifications.add(UserSpecification.genderSeeking(gender.name()));

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

        final PageRequest page = new PageRequest(
                pageNumber,
                pageSize,
                new Sort(
                    new Sort.Order(Sort.Direction.DESC, "profile.hasProfilePhoto"),
                    new Sort.Order(Sort.Direction.DESC, "createdDate")
                )
        );

        return ((Page<User>)userRepository.findAll(result, page)).map(user -> new RecipientProfile(user));
    }

    @CacheEvict(cacheNames = USER_BY_ID_CACHE, key = "#user.id")
    public User save(User user) {
        return userRepository.save(user);
    }

}
