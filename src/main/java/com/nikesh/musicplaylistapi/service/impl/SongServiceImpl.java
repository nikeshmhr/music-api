package com.nikesh.musicplaylistapi.service.impl;

import com.nikesh.musicplaylistapi.entities.Song;
import com.nikesh.musicplaylistapi.repositories.SongRepository;
import com.nikesh.musicplaylistapi.service.SongService;
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
public class SongServiceImpl implements SongService {

    private final SongRepository songRepository;

    @Autowired
    public SongServiceImpl(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    @Override
    public Song addSong(Song song) {
        return songRepository.save(song);
    }

    @Override
    public Song getSongById(Long songId) {
        return songRepository.findOne(songId);
    }

    @Override
    public Collection<Song> getAllSongs() {
        return songRepository.findAll();
    }

    @Override
    public Song updateSong(Song song) {
        return songRepository.save(song);
    }

    @Override
    public boolean isSongExists(Long songId) {
        return songRepository.findOne(songId) != null;
    }
}
