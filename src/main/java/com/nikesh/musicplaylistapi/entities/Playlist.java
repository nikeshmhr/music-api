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
@Table(name = "playlists")
@Getter
@Setter
public class Playlist extends ModelBase {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "playlist_name", length = 50, nullable = false)
    private String playlistName;
}
