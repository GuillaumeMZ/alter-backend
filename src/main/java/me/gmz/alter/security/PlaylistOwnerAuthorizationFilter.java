package me.gmz.alter.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import me.gmz.alter.entities.Playlist;
import me.gmz.alter.repositories.PlaylistRepository;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

public class PlaylistOwnerAuthorizationFilter extends OncePerRequestFilter {
    private final PlaylistRepository playlistRepository;

    public PlaylistOwnerAuthorizationFilter(PlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String playlistIdStr = request.getParameter("playlistId");
        if(playlistIdStr == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        int playlistId = 0;
        try {
            playlistId = Integer.valueOf(playlistIdStr);
        } catch (NumberFormatException _) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String token = "";
        try {
            token = Token.extractFromHeader(request.getHeader("Authorization"));
        } catch (MissingTokenException _) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        Optional<Playlist> playlistOptional = playlistRepository.findById(playlistId);
        if(playlistOptional.isEmpty() || !playlistOptional.get().getOwner().getToken().equals(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
