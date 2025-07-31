package me.gmz.alter.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "songs")
public class Song {
    public Song() { /* Empty constructor for Hibernate */ }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String album;

    private String artist;

    private String title;

    public Integer getId() {
        return id;
    }

    public String getAlbum() {
        return album;
    }

    public String getArtist() {
        return artist;
    }

    public String getTitle() {
        return title;
    }
}
