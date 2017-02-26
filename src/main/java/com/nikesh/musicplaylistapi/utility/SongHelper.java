package com.nikesh.musicplaylistapi.utility;

import com.nikesh.musicplaylistapi.dto.response.SongResponseDto;
import com.nikesh.musicplaylistapi.entities.Song;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;

/**
 * @author Nikesh Maharjan
 *         nikeshmhr@gmail.com
 */
public class SongHelper {

    public static Collection<SongResponseDto> convertToResponseList(Collection<Song> entities, ModelMapper modelMapper) {
        Type listType = new TypeToken<Collection<SongResponseDto>>() {
        }.getType();

        return modelMapper.map(entities, listType);
    }
}
