package com.nikesh.musicplaylistapi.service;

import com.nikesh.musicplaylistapi.entities.User;

import java.util.Collection;

/**
 * @author Nikesh Maharjan
 *         nikeshmhr@gmail.com
 */
public interface UserService {

    User createUser(User user);

    User getUserById(long userId);

    Collection<User> getAllUsers();

    User updateUser(User user);

}
