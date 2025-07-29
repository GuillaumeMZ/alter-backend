package me.gmz.alter.controllers;

import me.gmz.alter.dto.SignOptions;
import me.gmz.alter.entities.AppUser;
import me.gmz.alter.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserController(PasswordEncoder passwordEncoder, UserRepository userRepository) {
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
}
