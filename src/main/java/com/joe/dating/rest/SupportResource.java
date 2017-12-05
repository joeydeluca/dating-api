package com.joe.dating.rest;

import com.joe.dating.common.NotFoundException;
import com.joe.dating.common.Util;
import com.joe.dating.domain.passwordreset.PasswordResetRequest;
import com.joe.dating.domain.passwordreset.PasswordResetRequestRepository;
import com.joe.dating.domain.user.User;
import com.joe.dating.domain.user.UserRepository;
import com.joe.dating.email_sending.EmailSender;
import com.joe.dating.email_sending.PasswordResetEmail;
import com.joe.dating.email_sending.SupportEmail;
import com.joe.dating.security.AuthContext;
import com.joe.dating.security.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static com.joe.dating.config.CacheConfig.*;

@RestController
@RequestMapping("/api/support")
public class SupportResource {
    private static final Logger logger = LoggerFactory.getLogger(SupportResource.class);

    private final EmailSender emailSender;
    private final AuthService authService;
    private final UserRepository userRepository;
    private final PasswordResetRequestRepository passwordResetRequestRepository;

    public SupportResource(EmailSender emailSender, AuthService authService, UserRepository userRepository, PasswordResetRequestRepository passwordResetRequestRepository) {
        this.emailSender = emailSender;
        this.authService = authService;
        this.userRepository = userRepository;
        this.passwordResetRequestRepository = passwordResetRequestRepository;
    }

    @PostMapping("contact")
    public void contact(@RequestBody ContactDto contactDto,
                        @RequestHeader(value = "authorization", required = false) String authToken) {
        if(authToken != null) {
            AuthContext authContext = this.authService.verifyToken(authToken);
            contactDto.setUserId(authContext.getUserId());
        }

        emailSender.sendEmail(new SupportEmail(contactDto));
    }

    @PostMapping("password-reset")
    public void sendPasswordResetLink(@RequestBody String email) {
        logger.info("Initiating password reset workflow. email=" + email);
        String requestId = UUID.randomUUID().toString();

        Optional<User> user = userRepository.findByEmail(email).stream().findFirst();
        if(!user.isPresent()) {
            logger.info("Email does not exist.");
            return;
        }

        PasswordResetRequest passwordResetRequest = new PasswordResetRequest();
        passwordResetRequest.setEmail(email);
        passwordResetRequest.setUserId(user.get().getId());
        passwordResetRequest.setRequestId(requestId);
        passwordResetRequest.setRequestedDate(new Date());
        passwordResetRequestRepository.save(passwordResetRequest);

        emailSender.sendEmail(new PasswordResetEmail(requestId, email));
    }

    @PutMapping("password-reset")
    public void sendPasswordResetLink(@RequestBody PasswordReset passwordReset) {
        logger.info("Processing password reset link. requestId=" + passwordReset.requestId);
        PasswordResetRequest passwordResetRequest = passwordResetRequestRepository.findOne(passwordReset.requestId);
        if(passwordResetRequest == null || passwordResetRequest.getResetCompletedDate() != null) {
            throw new RuntimeException("Invalid request id");
        }

        User user = userRepository.findOne(passwordResetRequest.getUserId());
        if(user == null) {
            throw new NotFoundException();
        }

        user.setPassword(Util.md5(passwordReset.password));
        userRepository.save(user);

        passwordResetRequest.setResetCompletedDate(new Date());
        passwordResetRequestRepository.save(passwordResetRequest);

        logger.info("Password successfully reset. email=" + user.getEmail());
    }

    @PostMapping("log")
    public void log(@RequestBody String message, @RequestHeader(value = "authorization", required = false) String authToken) {
        AuthContext authContext = this.authService.verifyToken(authToken);

        logger.info("APP_LOG: user_id=" + authContext.getUserId() + "; " + message);
    }

    @CacheEvict(cacheNames = {
            PROFILE_SEARCH_CACHE,
            USER_BY_ID_CACHE,
            PROFILE_VIEWS_BY_ID_CACHE,
            FLIRTS_BY_ID_CACHE,
            FAVORITES_BY_ID_CACHE,
            MESSAGES_BY_ID_CACHE,
            PAYMENT_CACHE
    }, allEntries = true)
    @GetMapping("cache-evict")
    public void evictCache() {
        logger.info("Cache Evicted");
    }


    static class PasswordReset {
        public String requestId;
        public String password;
    }
}
