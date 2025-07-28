package me.gmz.alter.repositories;

import me.gmz.alter.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {}
