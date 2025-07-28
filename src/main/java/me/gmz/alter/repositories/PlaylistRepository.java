package me.gmz.alter.repositories;

import me.gmz.alter.entities.Playlist;
import org.springframework.data.repository.CrudRepository;

public interface PlaylistRepository extends CrudRepository<Playlist, Integer> {}
