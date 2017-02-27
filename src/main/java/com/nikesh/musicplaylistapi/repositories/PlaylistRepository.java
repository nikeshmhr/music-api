package com.nikesh.musicplaylistapi.repositories;

import com.nikesh.musicplaylistapi.entities.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Nikesh Maharjan
 *         nikeshmhr@gmail.com
 */
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
}
