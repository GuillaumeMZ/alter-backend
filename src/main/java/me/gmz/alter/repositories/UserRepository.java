package me.gmz.alter.repositories;

import me.gmz.alter.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, String> {
    Optional<User> findByToken(String token);
    Optional<User> findByUsernameAndPassword(String username, String password);
    void deleteByToken(String token);
}
