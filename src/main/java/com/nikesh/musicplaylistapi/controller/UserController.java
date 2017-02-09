package com.nikesh.musicplaylistapi.controller;

import com.nikesh.musicplaylistapi.constants.ApplicationConstants.ApiConstants;
import com.nikesh.musicplaylistapi.constants.ApplicationConstants.UserResourceConstants;
import com.nikesh.musicplaylistapi.dto.request.UserRequestDTO;
import com.nikesh.musicplaylistapi.dto.response.UserResponseDTO;
import com.nikesh.musicplaylistapi.entities.User;
import com.nikesh.musicplaylistapi.exception.NoRecordFoundException;
import com.nikesh.musicplaylistapi.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.Type;
import java.util.Collection;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.EXPECTATION_FAILED;

/**
 * @author Nikesh Maharjan
 *         nikeshmhr@gmail.com
 */
@RestController
@RequestMapping(ApiConstants.API_BASE_VERSION)
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /**
     * Resource URI to create new user.
     *
     * @param userRequest the {@link UserResponseDTO} object with user information.
     * @return information of created user.
     * @throws MethodArgumentNotValidException if value of request body is not valid.
     */
    @PostMapping(value = UserResourceConstants.USER_COLLECTION_BASE)
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody @Valid UserRequestDTO userRequest) throws MethodArgumentNotValidException {
        // Convert to entity.
        User user = modelMapper.map(userRequest, User.class);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        // Persist.
        User createdUser = userService.createUser(user);

        if (createdUser != null) {
            logger.info("User created successfully. Created User :: " + createdUser);
            UserResponseDTO createdUserDTO = modelMapper.map(createdUser, UserResponseDTO.class);

            return new ResponseEntity<>(createdUserDTO, CREATED);
        } else {
            logger.debug("Failed to create user with properties " + userRequest);

            return new ResponseEntity<>(EXPECTATION_FAILED);
        }
    }

    /**
     * Resource URI to retrieve all the available users.
     *
     * @return the list of {@link UserResponseDTO} object
     */
    @GetMapping(value = UserResourceConstants.USER_COLLECTION_BASE)
    public ResponseEntity<Collection<UserResponseDTO>> getUsers() {
        // Fetch users.
        Collection<User> allUsers = userService.getAllUsers();

        if (allUsers.isEmpty()) {
            throw new NoRecordFoundException("Users not found.");
        } else {
            Type listType = new TypeToken<Collection<UserResponseDTO>>() {
            }.getType();

            Collection<UserResponseDTO> responseDTO = modelMapper.map(allUsers, listType);

            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        }

    }

    /**
     * Resource URI to retrieve user by its userId.
     *
     * @param userId the user id to use for searching.
     * @return {@link UserResponseDTO} object with user's id and username.
     */
    @GetMapping(value = UserResourceConstants.USER_BY_ID)
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long userId) {
        // Fetch user by their id.
        User userById = userService.getUserById(userId);

        if (userById == null) {
            throw new NoRecordFoundException("User not found for id " + userById + "'.");
        } else {    // Otherwise convert to response and return
            UserResponseDTO responseDTO = modelMapper.map(userById, UserResponseDTO.class);

            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        }
    }

}
