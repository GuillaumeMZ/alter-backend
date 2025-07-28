package me.gmz.alter.controllers;

import me.gmz.alter.dto.SongFilter;
import me.gmz.alter.entities.Song;
import me.gmz.alter.repositories.SongRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SongController {
    private final SongRepository songRepository;

    public SongController(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    @GetMapping("/songs")
    public List<Song> getSongsMatchingFilter(@RequestBody SongFilter filter) {
        return songRepository.findByAlbumContainingOrArtistContainingOrTitleContaining(
                filter.filter(),
                filter.filter(),
                filter.filter()
        );
    }
}
