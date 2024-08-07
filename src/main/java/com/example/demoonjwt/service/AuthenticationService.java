package com.example.demoonjwt.service;

import com.example.demoonjwt.model.AuthenticationResponse;
import com.example.demoonjwt.model.Users;
import com.example.demoonjwt.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserRepository repository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository repository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }


    public AuthenticationResponse register(Users request){
        Users user = new Users();
        user.setUserName(request.getUsername());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setRole(request.getRole());

        user = repository.save(user);

        String token = jwtService.generateToken(user);

        return new AuthenticationResponse(token);

    }

    public AuthenticationResponse authenticate(Users request){
         authenticationManager.authenticate(
                 new UsernamePasswordAuthenticationToken(
                         request.getUsername(),
                         request.getPassword()
                 )
         );

          Users user = repository.findByUsername(request.getUsername()).orElseThrow();
          String token = jwtService.generateToken(user);

          return new AuthenticationResponse(token);

    }

}
