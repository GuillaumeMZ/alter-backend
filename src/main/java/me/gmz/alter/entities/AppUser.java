package me.gmz.alter.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.Set;

@Entity
@Table(name = "users")
public class AppUser {
    public AppUser() { /* Empty constructor for Hibernate */ }

    public AppUser(String username, String password, Boolean admin) {
        this.username = username;
        this.password = password;
        this.admin = admin;
    }

    @Id
    private String username;

    private String password;

    private Boolean admin;

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

    public Set<Playlist> getPlaylists() {
        return playlists;
    }
}
