package com.nikesh.musicplaylistapi.controller;

import com.nikesh.musicplaylistapi.constants.ApplicationConstants;
import com.nikesh.musicplaylistapi.dto.request.PlaylistRequestDto;
import com.nikesh.musicplaylistapi.dto.response.PlaylistResponseDto;
import com.nikesh.musicplaylistapi.entities.Playlist;
import com.nikesh.musicplaylistapi.exception.NoRecordFoundException;
import com.nikesh.musicplaylistapi.exception.OperationUnsuccessfulException;
import com.nikesh.musicplaylistapi.service.PlaylistService;
import com.nikesh.musicplaylistapi.utility.PlaylistHelper;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * @author Nikesh Maharjan
 *         nikeshmhr@gmail.com
 */
public class PlaylistController {
    private static final Logger logger = LoggerFactory.getLogger(PlaylistController.class);

    private final ModelMapper modelMapper;

    private final PlaylistService playlistService;

    @Autowired
    public PlaylistController(ModelMapper modelMapper, PlaylistService playlistService) {
        this.modelMapper = modelMapper;
        this.playlistService = playlistService;
    }

    /**
     * Resource URI to create new playlist.
     *
     * @param requestDto the {@link PlaylistRequestDto} object with playlist info
     * @return the {@link PlaylistResponseDto} object representing persisted entity
     */
    @PostMapping(value = ApplicationConstants.PlaylistResourceConstants.PLAYLIST_COLLECTION_BASE)
    public ResponseEntity<PlaylistResponseDto> createPlaylist(@RequestBody PlaylistRequestDto requestDto) {

        // Convert to entity and persist.
        Playlist playlist = modelMapper.map(requestDto, Playlist.class);

        playlist = playlistService.createPlaylist(playlist);

        // Convert to response and return.
        PlaylistResponseDto responseDto = modelMapper.map(playlist, PlaylistResponseDto.class);

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);

    }

    /**
     * Resource URI to retrieve the playlist by its id.
     *
     * @param playlistId the id to use while searching for playlist
     * @return the {@link PlaylistResponseDto} object that matches the playlistId
     * @throws NoRecordFoundException if playlist with playlistId not found
     */
    @GetMapping(value = ApplicationConstants.PlaylistResourceConstants.PLAYLIST_BY_ID)
    public ResponseEntity<PlaylistResponseDto> getPlaylistById(@PathVariable Long playlistId) throws NoRecordFoundException {
        Playlist playlistById = playlistService.getPlaylistById(playlistId);

        if (playlistById == null) {
            throw new NoRecordFoundException("Playlist not found for playlist id '" + playlistById + "'.");
        } else {    // OTHERWISE CONVERT AND RETURN RESPONSE
            return new ResponseEntity<>(modelMapper.map(playlistById, PlaylistResponseDto.class), HttpStatus.OK);
        }
    }

    /**
     * Resource URI to retrieve all the playlist.
     *
     * @return the collection of {@link PlaylistResponseDto} object representing {@link Playlist} domain object
     * @throws NoRecordFoundException if playlist not found
     */
    @GetMapping(value = ApplicationConstants.PlaylistResourceConstants.PLAYLIST_COLLECTION_BASE)
    public ResponseEntity<Collection<PlaylistResponseDto>> getAllPlaylist() throws NoRecordFoundException {
        Collection<Playlist> allPlaylist = playlistService.getAllPlaylist();

        if (allPlaylist.isEmpty()) {
            throw new NoRecordFoundException("Playlist not found.");
        } else {    // OTHERWISE CONVERT TO RESPONSE LIST AND RETURN
            return new ResponseEntity<>(PlaylistHelper.convertToResponseList(allPlaylist, modelMapper), HttpStatus.OK);
        }
    }

    /**
     * Resource URI to update the existing playlist information.
     *
     * @param playlistId the id of playlist to update
     * @param requestDto the {@link PlaylistRequestDto} object which holds modified playlist information
     * @return the {@link PlaylistResponseDto} object representing the updated {@link Playlist}
     * @throws OperationUnsuccessfulException if playlist with playlistId does not exists
     */
    @PutMapping(value = ApplicationConstants.PlaylistResourceConstants.PLAYLIST_BY_ID)
    public ResponseEntity<PlaylistResponseDto> updatePlaylist(@PathVariable Long playlistId,
                                                              @RequestBody PlaylistRequestDto requestDto) throws OperationUnsuccessfulException {
        // Check if playlist with given playlist id exists
        if (!playlistService.isPlaylistExists(playlistId)) {
            throw new OperationUnsuccessfulException("Could not update playlist because playlist with id '" + playlistId
                    + "' does not exists.");
        } else {
            // Retrieve the original object and make necessary changes.
            Playlist playlistById = playlistService.getPlaylistById(playlistId);
            playlistById.setPlaylistName(requestDto.getPlaylistName().trim());

            playlistById = playlistService.updatePlaylist(playlistById);

            return new ResponseEntity<>(modelMapper.map(playlistById, PlaylistResponseDto.class), HttpStatus.OK);
        }
    }

}
