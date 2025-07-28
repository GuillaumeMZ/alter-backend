package me.gmz.alter.controllers;

import me.gmz.alter.dto.SignOptions;
import me.gmz.alter.dto.SignResult;
import me.gmz.alter.entities.User;
import me.gmz.alter.repositories.UserRepository;
import me.gmz.alter.security.Token;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/users/signup")
    public SignResult signup(@RequestBody SignOptions signOptions) {
        String username = signOptions.username();
        String password = signOptions.password();
        String hashedPassword = password; //TODO
        String token = Token.generate();

        userRepository.save(new User(username, hashedPassword, false, token));

        return new SignResult(token);
    }

    @PostMapping("/users/signin")
    public ResponseEntity<SignResult> signin(@RequestBody SignOptions signOptions) {
        String username = signOptions.username();
        String password = signOptions.password();
        String hashedPassword = password; //TODO

        Optional<User> userOptional = userRepository.findByUsernameAndPassword(username, hashedPassword);
        return userOptional
                .map(user -> new ResponseEntity<>(new SignResult(user.getToken()), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }
}
