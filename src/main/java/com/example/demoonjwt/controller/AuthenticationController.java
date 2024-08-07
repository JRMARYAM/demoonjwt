package com.example.demoonjwt.controller;


import com.example.demoonjwt.model.AuthenticationResponse;
import com.example.demoonjwt.model.Users;
import com.example.demoonjwt.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    private final AuthenticationService authService;

    public AuthenticationController(AuthenticationService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody Users request
            ) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login (
            @RequestBody Users request
    ){
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @GetMapping("/admin_only")
    public String admin_only (
            @RequestBody Users request
    ){
        return ("ADMIN Accessible");
    }


}
