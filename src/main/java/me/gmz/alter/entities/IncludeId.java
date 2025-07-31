package me.gmz.alter.entities;

import java.io.Serializable;
import java.util.Objects;

public class IncludeId implements Serializable {
    private Integer playlist;

    private Integer song;

    public IncludeId() {}

    public IncludeId(Integer playlist, Integer song) {
        this.playlist = playlist;
        this.song = song;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IncludeId that)) return false;
        return Objects.equals(playlist, that.playlist) && Objects.equals(song, that.song);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playlist, song);
    }
}
