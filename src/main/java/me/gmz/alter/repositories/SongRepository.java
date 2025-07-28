package me.gmz.alter.repositories;

import me.gmz.alter.entities.Song;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SongRepository extends CrudRepository<Song, Integer> {
    List<Song> findByAlbumContainingOrArtistContainingOrTitleContaining(String album, String artist, String title);
}
