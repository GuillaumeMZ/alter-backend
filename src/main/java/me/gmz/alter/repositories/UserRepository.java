package me.gmz.alter.repositories;

import me.gmz.alter.entities.AppUser;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<AppUser, String> {}
