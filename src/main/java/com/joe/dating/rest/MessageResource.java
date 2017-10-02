package com.joe.dating.rest;

import com.joe.dating.captcha_verification.CaptchaService;
import com.joe.dating.domain.user.User;
import com.joe.dating.domain.user.UserService;
import com.joe.dating.domain.user.models.Profile;
import com.joe.dating.security.AuthContext;
import com.joe.dating.security.AuthService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Joe Deluca on 11/21/2016.
 */
@RestController
@RequestMapping("/api/messages")
public class MessageResource {

    private UserService userService;
    private CaptchaService captchaService;
    private AuthService authService;
    private boolean isCaptchaEnabled;

    private final int DEFAULT_PAGE_SIZE = 3;

    public MessageResource(
            UserService userService,
            CaptchaService captchaService,
            AuthService authService,
            @Value("${captcha.enable}") boolean isCaptchaEnabled) {
        this.userService = userService;
        this.captchaService = captchaService;
        this.authService = authService;
        this.isCaptchaEnabled = isCaptchaEnabled;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findOne(
            @PathVariable("id") Long id,
            @RequestHeader(value = "authorization") String authToken) {

        AuthContext authContext = this.authService.verifyToken(authToken);
        if(!id.equals(authContext.getUserId())) {
            throw new IllegalArgumentException("Unauthorized");
        }

        User user = userService.findOne(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<AuthContext> create(@RequestBody User user) {
        AuthContext authContext = userService.create(user.getEmail(), user.getUsername(), user.getPassword(), user.getGender(), user.getGenderSeeking());
        return ResponseEntity.ok(authContext);
    }

    @PutMapping("/{id}/join-completion")
    public ResponseEntity<User> joinCompletion(
            @PathVariable("id") Long userId,
            @RequestBody User user,
            @RequestHeader(value = "authorization") String authToken,
            @RequestHeader(value = "captcha", required = false) String captcha) {

        this.authService.verifyToken(authToken);

        if (captcha != null && isCaptchaEnabled && !captchaService.verifyCaptcha(captcha)) {
            throw new RuntimeException("Captcha verification failed");
        }

        return ResponseEntity.ok(userService.completeUserJoin(userId, user));
    }

    @PutMapping("/{id}/profile")
    public ResponseEntity<User> updateProfile(
            @PathVariable("id") Long userId,
            @RequestBody Profile profile,
            @RequestHeader(value = "authorization") String authToken) {

        this.authService.verifyToken(authToken);

        return ResponseEntity.ok(userService.updateProfile(userId, profile));
    }

    @GetMapping("/{id}/profile")
    public ResponseEntity<Profile> findProfile(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.findOne(id).getProfile());
    }

//    @GetMapping("/profiles")
//    public ResponseEntity<Page<RecipientProfile>> searchProfiles(
//            @RequestHeader(value = "authorization") String authToken,
//            @PageableDefault(size = DEFAULT_PAGE_SIZE)
//            Pageable pageable,
//            @RequestParam(value = "age-from", defaultValue = "18") int ageFrom,
//            @RequestParam(value = "age-to", defaultValue = "65") int ageTo,
//            @RequestParam(value = "country") String countryId,
//            @RequestParam(value = "region", required = false) String regionId,
//            @RequestParam(value = "city", required = false) String cityId
//
//
//    ) {
//
//        this.authService.verifyToken(authToken);
//
//        return ResponseEntity.ok(userService.searchProfiles(pageable, ageFrom, ageTo, countryId, regionId, cityId)
//                        .map(user -> new RecipientProfile(user)));
//    }



}
