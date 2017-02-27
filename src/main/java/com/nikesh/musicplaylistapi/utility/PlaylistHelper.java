package com.nikesh.musicplaylistapi.utility;

import com.nikesh.musicplaylistapi.dto.response.PlaylistResponseDto;
import com.nikesh.musicplaylistapi.entities.Playlist;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;

/**
 * @author Nikesh Maharjan
 *         nikeshmhr@gmail.com
 */
public class PlaylistHelper {

    public static Collection<PlaylistResponseDto> convertToResponseList(Collection<Playlist> entities, ModelMapper modelMapper) {
        Type listType = new TypeToken<Collection<PlaylistResponseDto>>() {
        }.getType();

        return modelMapper.map(entities, listType);
    }

}
