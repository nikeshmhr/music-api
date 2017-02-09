package com.nikesh.musicplaylistapi.repositories;

import com.nikesh.musicplaylistapi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Nikesh Maharjan
 *         nikeshmhr@gmail.com
 */
public interface UserRepository extends JpaRepository<User, Long> {

}
