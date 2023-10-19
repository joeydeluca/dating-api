package com.joe.dating.rest;

import com.joe.dating.domain.user.UserService;
import com.joe.dating.security.AuthContext;
import com.joe.dating.security.AuthService;
import com.joe.dating.security.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/chat")
public class ChatResource {

    private AuthService authService;
    private UserService userService;
    private JwtService jwtService;



    public ChatResource(
            AuthService authService,
            UserService userService,
            JwtService jwtService) {
        this.authService = authService;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @GetMapping("/init")
    public ResponseEntity<String> auth(
            @RequestHeader(value = "authorization") String authToken) {

        // Very user os logged into website
        AuthContext authContext = this.authService.verifyToken(authToken);

        // Generate Frend JWT
        String frendAIJWT = jwtService.createFrendAIJwt(authContext.getUserId(), authContext.getUsername(), false, authContext.getGender(), authContext.getGenderSeeking());

        return ResponseEntity.ok(frendAIJWT);
    }
}
