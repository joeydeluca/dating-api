package com.joe.dating.rest;

import com.joe.dating.captcha_verification.CaptchaService;
import com.joe.dating.domain.user.UserService;
import com.joe.dating.domain.user.User;
import com.joe.dating.domain.user.models.Profile;
import com.joe.dating.security.AuthContext;
import com.joe.dating.security.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Joe Deluca on 11/21/2016.
 */
@RestController
@RequestMapping("/api/users")
public class UserResource {

    private UserService userService;
    private CaptchaService captchaService;
    private AuthService authService;
    private boolean isCaptchaEnabled;

    public UserResource(
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
    public ResponseEntity<User> findOne(@PathVariable("id") Long id) {
        User user = userService.findOne(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<User>> findMany() {
        List<User> users = userService.search(18, 60, null, null, null);
        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<AuthContext> create(@RequestBody User user) {
        AuthContext authContext = userService.create(user.getEmail(), user.getUsername(), user.getPassword(), user.getGender(), user.getGenderSeeking());
        return ResponseEntity.ok(authContext);
    }

    @PutMapping("/{id}/profile")
    public ResponseEntity<Profile> updateProfile(
            @PathVariable("id") Long userId,
            @RequestBody Profile profile,
            @RequestHeader(value = "authorization") String authToken,
            @RequestHeader(value = "captcha", required = false) String captcha) {

        this.authService.verifyToken(authToken);

        if (captcha != null && isCaptchaEnabled && !captchaService.verifyCaptcha(captcha)) {
            throw new RuntimeException("Captcha verification failed");
        }

        User user = userService.updateProfile(userId, profile);

        return ResponseEntity.ok(user.getProfile());
    }



}
