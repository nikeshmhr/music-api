package com.nikesh.musicplaylistapi.repositories;

import com.nikesh.musicplaylistapi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author Nikesh Maharjan
 *         nikeshmhr@gmail.com
 */
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.username=:username AND u.id NOT IN (:userId)")
    List<User> findByUsernameAndIgnoreUserId(@Param("username") String newUserName, @Param("userId") Long existingUserId);
}
