package me.gmz.alter.repositories;

import me.gmz.alter.entities.Song;
import org.springframework.data.repository.CrudRepository;

public interface SongRepository extends CrudRepository<Song, Integer> {}
