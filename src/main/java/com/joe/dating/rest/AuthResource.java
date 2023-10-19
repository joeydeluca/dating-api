package com.joe.dating.rest;

import com.joe.dating.domain.user.User;
import com.joe.dating.domain.user.UserService;
import com.joe.dating.security.AuthContext;
import com.joe.dating.security.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Joe Deluca on 11/21/2016.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthResource {

    private final AuthService authService;
    private final UserService userService;

    public AuthResource(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    public AuthService getAuthService() {
        return authService;
    }

    @PostMapping
    public ResponseEntity<AuthContext> create(@RequestBody AuthDto authDto) {
        return ResponseEntity.ok(
                authService.createAuthContext(authDto.getEmail(), authDto.getPassword())
        );
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthContext> update(@RequestHeader(value = "authorization") String authToken) {

        AuthContext authContext = this.authService.verifyToken(authToken);

        User user = userService.findOne(authContext.getUserId());

        return ResponseEntity.ok(
                authService.createAuthContext(
                        user.getId(),
                        user.isPaid(),
                        user.getUsername(),
                        user.getCompletionStatus(),
                        user.getSiteId(),
                        user.getGender(),
                        user.getGenderSeeking()
                )
        );
    }

}
