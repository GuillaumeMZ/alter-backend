package me.gmz.alter.controllers;

import me.gmz.alter.dto.SignOptions;
import me.gmz.alter.dto.SignResult;
import me.gmz.alter.entities.AppUser;
import me.gmz.alter.repositories.UserRepository;
import me.gmz.alter.services.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserController(JwtService jwtService, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody SignOptions signOptions) {
        if(userRepository.findById(signOptions.username()).isPresent()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        AppUser user = new AppUser(signOptions.username(), passwordEncoder.encode(signOptions.password()), false);
        userRepository.save(user);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public SignResult login(Authentication authentication) {
        return new SignResult(jwtService.generateToken(authentication));
    }
}
