package com.nikesh.musicplaylistapi.controller;

import com.nikesh.musicplaylistapi.constants.ApplicationConstants.ApiConstants;
import com.nikesh.musicplaylistapi.constants.ApplicationConstants.UserResourceConstants;
import com.nikesh.musicplaylistapi.dto.request.UserRequestDTO;
import com.nikesh.musicplaylistapi.dto.response.UserResponseDTO;
import com.nikesh.musicplaylistapi.entities.User;
import com.nikesh.musicplaylistapi.exception.BadDataException;
import com.nikesh.musicplaylistapi.exception.DuplicateDataException;
import com.nikesh.musicplaylistapi.exception.NoRecordFoundException;
import com.nikesh.musicplaylistapi.service.UserService;
import com.nikesh.musicplaylistapi.utility.UserHelper;
import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.EXPECTATION_FAILED;

/**
 * Endpoints to handle {@link User} related activities.
 *
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
    private ServletContext context;

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
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody @Valid UserRequestDTO userRequest)
            throws MethodArgumentNotValidException {

        // Check if user with provided username already exists.
        if (userService.isUsernameExists(userRequest.getUsername())) {
            throw new DuplicateDataException("Username '" + userRequest.getUsername() + "' already exists.");
        } else {
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
            return new ResponseEntity<>(UserHelper.convertToResponseList(allUsers, modelMapper), HttpStatus.OK);
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

    /**
     * Resource URI to update the information of existing user.
     *
     * @param userId         id of the user to update details for
     * @param userRequestDTO updated information of user
     * @return updated user information
     * @throws MethodArgumentNotValidException if any of the properties of request body is not valid
     */
    @PutMapping(value = UserResourceConstants.USER_BY_ID)
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long userId, @RequestBody @Valid UserRequestDTO userRequestDTO)
            throws MethodArgumentNotValidException {

        // Check if user with provided id exists.
        // Only perform update action if it exists.
        if (userService.isUserExists(userId)) {
            // If provided username already exists then throw duplicate username exception
            if (!userService.isUsernameValidForExistingUser(userRequestDTO.getUsername(), userId)) {
                throw new DuplicateDataException("Username '" + userRequestDTO.getUsername() + "' already exists.");
            } else {
                // Convert request object to persistable entity.
                User user = modelMapper.map(userRequestDTO, User.class);
                user.setId(userId);
                user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

                // Update/Persist with id set (Primary key set)...
                User persistedUser = userService.updateUser(user);

                return new ResponseEntity<>(modelMapper.map(persistedUser, UserResponseDTO.class), HttpStatus.OK);
            }
        } else {    // Otherwise throw BadRequest exception.
            logger.debug("User with user id '" + userId + "' does not exists.");
            throw new BadDataException("User with user id '" + userId + "' does not exists.");
        }
    }

    @GetMapping(value = "/audio")
    public ResponseEntity<InputStreamResource> getAudio() throws IOException {
        ClassPathResource res = new ClassPathResource("file.mp3");

        return ResponseEntity.ok().contentLength(res.contentLength()).contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new InputStreamResource(res.getInputStream()));
    }

    @GetMapping(value = "/audionew")
    public ResponseEntity<String> getAudioNew() throws IOException {
        ClassPathResource res = new ClassPathResource("file.mp3");


        InputStream is = res.getInputStream();
        String base64Encoded = Base64.encodeBase64String(IOUtils.toByteArray(is));

        System.out.println("BASE64 ENCODED :: " + base64Encoded);

        return new ResponseEntity<>(base64Encoded, HttpStatus.OK);
    }

}
