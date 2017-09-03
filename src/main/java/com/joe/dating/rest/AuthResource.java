package com.joe.dating.rest;

import com.joe.dating.security.AuthContext;
import com.joe.dating.security.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Joe Deluca on 11/21/2016.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthResource {

    private final AuthService authService;

    public AuthResource(AuthService authService) {
        this.authService = authService;
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

}
