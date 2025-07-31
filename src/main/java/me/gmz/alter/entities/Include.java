package me.gmz.alter.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "includes")
@IdClass(IncludeId.class)
public class Include {
    @Id
    @ManyToOne
    @JoinColumn(name = "playlist_id")
    private Playlist playlist;

    @Id
    @ManyToOne
    @JoinColumn(name = "song_id")
    private Song song;

    private Integer position;
}
