package com.nikesh.musicplaylistapi.service.impl;

import com.nikesh.musicplaylistapi.entities.Playlist;
import com.nikesh.musicplaylistapi.repositories.PlaylistRepository;
import com.nikesh.musicplaylistapi.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

/**
 * @author Nikesh Maharjan
 *         nikeshmhr@gmail.com
 */
@Service
@Transactional
public class PlaylistServiceImpl implements PlaylistService {

    private final PlaylistRepository playlistRepository;

    @Autowired
    public PlaylistServiceImpl(PlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
    }

    @Override
    public Playlist createPlaylist(Playlist playlist) {
        return playlistRepository.save(playlist);
    }

    @Override
    public Playlist getPlaylistById(Long playlistId) {
        return playlistRepository.findOne(playlistId);
    }

    @Override
    public Collection<Playlist> getAllPlaylist() {
        return playlistRepository.findAll();
    }

    @Override
    public Playlist updatePlaylist(Playlist playlist) {
        return playlistRepository.save(playlist);
    }

    @Override
    public boolean isPlaylistExists(Long playlistId) {
        return playlistRepository.findOne(playlistId) != null;
    }
}
