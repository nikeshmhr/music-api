package com.nikesh.musicplaylistapi.service.impl;

import com.nikesh.musicplaylistapi.entities.User;
import com.nikesh.musicplaylistapi.repositories.UserRepository;
import com.nikesh.musicplaylistapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * @author Nikesh Maharjan
 *         nikeshmhr@gmail.com
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;

    @Autowired
    public UserServiceImpl(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public User createUser(User user) {
        return userRepo.save(user);
    }

    @Override
    public User getUserById(long userId) {
        return userRepo.findOne(userId);
    }

    @Override
    public Collection<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public User updateUser(User user) {
        return userRepo.save(user);
    }

    @Override
    public boolean isUserExists(Long userId) {
        return userRepo.findOne(userId) != null;
    }

    @Override
    public boolean isUsernameExists(String username) {
        return userRepo.findByUsername(username) != null;
    }

    @Override
    public boolean isUsernameValidForExistingUser(String newUserName, Long existingUserId) {
        List<User> existingUserWithNewUsername = userRepo.findByUsernameAndIgnoreUserId(newUserName, existingUserId);
        return existingUserWithNewUsername.isEmpty();
    }

}
