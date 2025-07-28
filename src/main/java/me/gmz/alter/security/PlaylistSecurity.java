package me.gmz.alter.security;

import me.gmz.alter.repositories.PlaylistRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("playlistSecurity")
public class PlaylistSecurity {
    private final PlaylistRepository playlistRepository;

    public PlaylistSecurity(PlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
    }

    public boolean isOwner(Authentication authentication, Integer playlistId) {
        String username = authentication.getName();
        return playlistRepository.findById(playlistId)
                .map(playlist -> playlist.getOwner().getUsername().equals(username))
                .orElse(false);
    }
}
