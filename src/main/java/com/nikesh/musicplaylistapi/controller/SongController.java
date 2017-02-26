package com.nikesh.musicplaylistapi.controller;

import com.nikesh.musicplaylistapi.constants.ApplicationConstants;
import com.nikesh.musicplaylistapi.dto.request.SongRequestDto;
import com.nikesh.musicplaylistapi.dto.request.SongUpdateRequestDto;
import com.nikesh.musicplaylistapi.dto.response.SongResponseDto;
import com.nikesh.musicplaylistapi.entities.Song;
import com.nikesh.musicplaylistapi.exception.BadDataException;
import com.nikesh.musicplaylistapi.exception.NoRecordFoundException;
import com.nikesh.musicplaylistapi.service.SongService;
import com.nikesh.musicplaylistapi.utility.SongHelper;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * @author Nikesh Maharjan
 *         nikeshmhr@gmail.com
 */
@RestController
@RequestMapping(ApplicationConstants.ApiConstants.API_BASE_VERSION)
public class SongController {

    private static final Logger logger = LoggerFactory.getLogger(SongController.class);

    private final ModelMapper modelMapper;

    private final SongService songService;

    @Autowired
    public SongController(ModelMapper modelMapper, SongService songService) {
        this.modelMapper = modelMapper;
        this.songService = songService;
    }

    /**
     * Resource URI to add new song to the song list.
     *
     * @param requestDto the {@link SongRequestDto} object with song data to add
     * @return the {@link SongResponseDto} object containing persisted information
     * @throws MethodArgumentNotValidException if {@link SongRequestDto} contains information that is not valid or
     *                                         satisfies the provided validation criteria
     */
    @PostMapping(value = ApplicationConstants.SongResourceConstants.SONG_COLLECTION_BASE)
    public ResponseEntity<SongResponseDto> addSong(
            @RequestBody SongRequestDto requestDto)
            throws MethodArgumentNotValidException {
        // TODO add @Valid in request body..

        // Convert request into entity
        Song song = modelMapper.map(requestDto, Song.class);

        // Persist into database
        song = songService.addSong(song);

        // Convert persisted object to its corresponding response dto.
        SongResponseDto responseDto = modelMapper.map(song, SongResponseDto.class);

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);

    }

    /**
     * Resource URI to retrieve song by its id
     *
     * @param songId the id of the song to use while retrieving a song
     * @return the {@link SongResponseDto} object containing the information of song that matches the songId
     * @throws NoRecordFoundException if song with songId not found
     */
    @GetMapping(value = ApplicationConstants.SongResourceConstants.SONG_BY_ID)
    public ResponseEntity<SongResponseDto> getSongById(@PathVariable Long songId)
            throws NoRecordFoundException {

        Song songById = songService.getSongById(songId);

        if (songById == null) {
            logger.debug("Song with id '" + songId + "' not found.");

            throw new NoRecordFoundException("Song with id '" + songId + "' not found.");
        } else {
            // Convert to response dto and return response.
            SongResponseDto responseDto = modelMapper.map(songById, SongResponseDto.class);

            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }
    }

    /**
     * Resource URI to retrieve all the available songs
     *
     * @return the list of {@link SongResponseDto} objects containing all songs
     * @throws NoRecordFoundException if songs not found
     */
    @GetMapping(value = ApplicationConstants.SongResourceConstants.SONG_COLLECTION_BASE)
    public ResponseEntity<Collection<SongResponseDto>> getAllSongs() throws NoRecordFoundException {

        Collection<Song> allSongs = songService.getAllSongs();

        if (allSongs.isEmpty()) {
            logger.debug("Songs not found.");

            throw new NoRecordFoundException("Songs not found.");
        } else {
            // Convert to response list and return.
            return new ResponseEntity<>(SongHelper.convertToResponseList(allSongs, modelMapper), HttpStatus.OK);
        }
    }

    /**
     * Resource URI to update the information of existing song
     *
     * @param songId          the id of the song to update
     * @param updatedSongInfo the {@link SongUpdateRequestDto} object with modified song information
     * @return the {@link SongResponseDto} object containing updated song information
     * @throws MethodArgumentNotValidException if {@link SongUpdateRequestDto} property value does not satisfies its criteria
     * @throws BadDataException                if songId provided in path varialbe does not matches its request body's
     *                                         songId or if song with provided songId does not exists
     */
    @PutMapping(value = ApplicationConstants.SongResourceConstants.SONG_BY_ID)
    public ResponseEntity<SongResponseDto> updateSong(@PathVariable Long songId,
                                                      @RequestBody SongUpdateRequestDto updatedSongInfo) throws MethodArgumentNotValidException, BadDataException {
        // Check if songId provided in path variable matches the request body's songId
        // If not throw BadDataException
        if (!songId.equals(updatedSongInfo.getId())) {
            logger.debug("Song id provided in endpoint url doesn't matches its request body.");

            throw new BadDataException("Song id provided in endpoint url doesn't matches its request body.");
        } else {    // Otherwise perfom the update operation
            // Check if song with provide songId exists.
            // If not throw exception
            if (!songService.isSongExists(songId)) {
                logger.debug("Song with song id '" + songId + "' does not exists.");

                throw new BadDataException("Song with song id '" + songId + "' does not exists.");
            } else {    // Otherwise convert to entity and update.

                Song modifiedSong = modelMapper.map(updatedSongInfo, Song.class);

                modifiedSong = songService.updateSong(modifiedSong);

                // Convert to response and return
                SongResponseDto responseDto = modelMapper.map(modifiedSong, SongResponseDto.class);

                return new ResponseEntity<>(responseDto, HttpStatus.OK);
            }
        }
    }
}
