package com.nikesh.musicplaylistapi.entities;

import com.nikesh.musicplaylistapi.core.ModelBase;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author Nikesh Maharjan
 *         nikeshmhr@gmail.com
 */
@Entity
@Table(name = "playlist_songs")
@Getter
@Setter
public class PlaylistSong extends ModelBase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "playlist_id")
    private Playlist playlist;

    @ManyToOne
    @JoinColumn(name = "song_id")
    private Song song;

}
