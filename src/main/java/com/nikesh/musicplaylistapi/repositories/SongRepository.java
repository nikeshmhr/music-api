package com.nikesh.musicplaylistapi.repositories;

import com.nikesh.musicplaylistapi.entities.Song;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Nikesh Maharjan
 *         nikeshmhr@gmail.com
 */
public interface SongRepository extends JpaRepository<Song, Long> {
}
