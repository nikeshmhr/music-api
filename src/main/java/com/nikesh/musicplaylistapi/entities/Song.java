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
@Table(name = "songs")
@Getter
@Setter
public class Song extends ModelBase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "song_title")
    private String title;

    @Column(name = "artist")
    private String artist;

    @Column(name = "album_name")
    private String albumName;

    @Column(name = "song_duration")
    private String songDuration;

    @Column(name = "thumbnail_image")
    private String thumbnailImage;


}
