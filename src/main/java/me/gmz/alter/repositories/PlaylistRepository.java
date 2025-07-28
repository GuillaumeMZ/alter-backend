package me.gmz.alter.repositories;

import me.gmz.alter.entities.Playlist;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PlaylistRepository extends CrudRepository<Playlist, Integer> {
    public List<Playlist> findAllByOwnerToken(String ownerToken);
}
