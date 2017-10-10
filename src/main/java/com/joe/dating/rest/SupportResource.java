package com.joe.dating.rest;

import com.joe.dating.email_sending.EmailSender;
import com.joe.dating.email_sending.SupportEmail;
import com.joe.dating.security.AuthContext;
import com.joe.dating.security.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import static com.joe.dating.config.CacheConfig.*;

@RestController
@RequestMapping("/api/support")
public class SupportResource {
    private static final Logger logger = LoggerFactory.getLogger(SupportResource.class);

    private final EmailSender emailSender;
    private AuthService authService;

    public SupportResource(EmailSender emailSender, AuthService authService) {
        this.emailSender = emailSender;
        this.authService = authService;
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

    @CacheEvict(cacheNames = {
            PROFILE_SEARCH_CACHE,
            USER_BY_ID_CACHE,
            PROFILE_VIEWS_BY_ID_CACHE,
            FLIRTS_BY_ID_CACHE,
            FAVORITES_BY_ID_CACHE,
            MESSAGES_BY_ID_CACHE
    }, allEntries = true)
    @GetMapping("cache-evict")
    public void evictCache() {
        logger.info("Cache Evicted");
    }
}
