package me.gmz.alter.controllers;

import me.gmz.alter.dto.PlaylistCreationOptions;
import me.gmz.alter.entities.Playlist;
import me.gmz.alter.entities.User;
import me.gmz.alter.repositories.PlaylistRepository;
import me.gmz.alter.repositories.UserRepository;
import me.gmz.alter.security.MissingTokenException;
import me.gmz.alter.security.Token;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class PlaylistController {
    private final PlaylistRepository playlistRepository;
    private final UserRepository userRepository;

    public PlaylistController(PlaylistRepository playlistRepository, UserRepository userRepository) {
        this.playlistRepository = playlistRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/playlists")
    public List<Playlist> getUserPlaylists(@RequestHeader("Authorization") String authorizationHeader) throws MissingTokenException {
        String token = Token.extractFromHeader(authorizationHeader);

        return playlistRepository.findAllByOwnerToken(token);
    }

    @PostMapping("/playlists")
    public void createNewPlaylist(@RequestHeader("Authorization") String authorizationHeader, @RequestBody PlaylistCreationOptions playlistCreationOptions) throws MissingTokenException {
        String token = Token.extractFromHeader(authorizationHeader);
        Optional<User> owner = userRepository.findByToken(token); //guaranteed to exist thanks to middleware
        Playlist playlist = new Playlist(playlistCreationOptions.name(), owner.get());
        playlistRepository.save(playlist);
    }

    @GetMapping("/playlists/{playlistId}")
    public Playlist getSpecificPlaylist(@RequestHeader("Authorization") String authorizationHeader, @PathVariable("playlistId") Integer playlistId) throws MissingTokenException {
        return playlistRepository.findById(playlistId).orElse(null);
    }
}
