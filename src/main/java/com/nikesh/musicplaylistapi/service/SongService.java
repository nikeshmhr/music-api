package com.nikesh.musicplaylistapi.service;

import com.nikesh.musicplaylistapi.entities.Song;

import java.util.Collection;

/**
 * @author Nikesh Maharjan
 *         nikeshmhr@gmail.com
 */
public interface SongService {

    Song addSong(Song song);

    Song getSongById(Long songId);

    Collection<Song> getAllSongs();

    Song updateSong(Song song);

    boolean isSongExists(Long songId);

}
