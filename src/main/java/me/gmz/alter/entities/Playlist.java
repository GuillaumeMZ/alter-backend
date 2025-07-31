package me.gmz.alter.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @OneToMany(mappedBy = "playlist")
    private Set<Include> includes;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public AppUser getOwner() {
        return owner;
    }

    public Set<Include> getSongs() {
        return includes;
    }
}
