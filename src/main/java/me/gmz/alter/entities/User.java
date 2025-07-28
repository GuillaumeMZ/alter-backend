package me.gmz.alter.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    public User() { /* Empty constructor for Hibernate */ }

    public User(String username, String password, Boolean admin, String token) {
        this.username = username;
        this.password = password;
        this.admin = admin;
        this.token = token;
    }

    @Id
    private String username;

    private String password;

    private Boolean admin;

    private String token;

    @OneToMany(mappedBy = "owner")
    private Set<Playlist> playlists;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public String getToken() {
        return token;
    }

    public Set<Playlist> getPlaylists() {
        return playlists;
    }
}
