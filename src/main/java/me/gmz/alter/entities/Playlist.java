package me.gmz.alter.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "playlists")
public class Playlist {
    public Playlist() { /* Empty constructor for Hibernate */ }

    public Playlist(String name, AppUser owner) {
        this.name = name;
        this.owner = owner;
    }

    public Playlist(Integer id, String name, AppUser owner) {
        this.id = id;
        this.name = name;
        this.owner = owner;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "owner_username")
    @JsonIgnore
    private AppUser owner;

    @ManyToMany
    @JoinTable(
            name = "includes",
            joinColumns = @JoinColumn(name = "playlist_id"),
            inverseJoinColumns = @JoinColumn(name = "song_id")
    )
    @JsonIgnoreProperties(value = "playlists")
    private Set<Song> songs;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public AppUser getOwner() {
        return owner;
    }

    public Set<Song> getSongs() {
        return songs;
    }
}
