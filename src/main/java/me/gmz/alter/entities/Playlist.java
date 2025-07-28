package me.gmz.alter.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "playlists")
public class Playlist {
    public Playlist() { /* Empty constructor for Hibernate */ }

    public Playlist(String name, User owner) {
        this.name = name;
        this.owner = owner;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "owner_username")
    private User owner;

    @ManyToMany
    @JoinTable(
            name = "includes",
            joinColumns = @JoinColumn(name = "playlist_id"),
            inverseJoinColumns = @JoinColumn(name = "song_id")
    )
    private Set<Song> songs;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public User getOwner() {
        return owner;
    }

    public Set<Song> getSongs() {
        return songs;
    }
}
