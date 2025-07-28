package me.gmz.alter.controllers;

import me.gmz.alter.dto.SignOptions;
import me.gmz.alter.dto.SignResult;
import me.gmz.alter.entities.User;
import me.gmz.alter.repositories.UserRepository;
import me.gmz.alter.security.Token;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
