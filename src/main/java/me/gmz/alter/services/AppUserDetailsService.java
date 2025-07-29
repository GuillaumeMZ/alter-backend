package me.gmz.alter.services;

import me.gmz.alter.entities.AppUser;
import me.gmz.alter.repositories.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;

@Service
public class AppUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public AppUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException("User not found."));

        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getAdmin() ? "admin" : "user")
                .build();
    }
}
