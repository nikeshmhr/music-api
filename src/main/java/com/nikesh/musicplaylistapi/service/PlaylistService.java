package com.nikesh.musicplaylistapi.service;

import com.nikesh.musicplaylistapi.entities.Playlist;

import java.util.Collection;

/**
 * @author Nikesh Maharjan
 *         nikeshmhr@gmail.com
 */
public interface PlaylistService {

    Playlist createPlaylist(Playlist playlist);

    Playlist getPlaylistById(Long playlistId);

    Collection<Playlist> getAllPlaylist();

    Playlist updatePlaylist(Playlist playlist);

    boolean isPlaylistExists(Long playlistId);

}
