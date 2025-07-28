package me.gmz.alter.controllers;

import me.gmz.alter.entities.Playlist;
import me.gmz.alter.repositories.PlaylistRepository;
import me.gmz.alter.security.MissingTokenException;
import me.gmz.alter.security.Token;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PlaylistController {
    private final PlaylistRepository playlistRepository;

    public PlaylistController(PlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
    }

    @GetMapping("/playlists")
    public List<Playlist> getUserPlaylists(@RequestHeader("Authorization") String authorizationHeader) throws MissingTokenException {
        String token = Token.extractFromHeader(authorizationHeader);

        return playlistRepository.findAllByOwnerToken(token);
    }
}
