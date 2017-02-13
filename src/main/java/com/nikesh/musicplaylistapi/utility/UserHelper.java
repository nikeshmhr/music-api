package com.nikesh.musicplaylistapi.utility;

import com.nikesh.musicplaylistapi.dto.response.UserResponseDTO;
import com.nikesh.musicplaylistapi.entities.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;

/**
 * Helper class that will assist the service with certain simple and common tasks.
 *
 * @author Nikesh Maharjan
 *         nikeshmhr@gmail.com
 */
public class UserHelper {

    public static Collection<UserResponseDTO> convertToResponseList(Collection<User> entities, ModelMapper modelMapper) {
        Type listType = new TypeToken<Collection<UserResponseDTO>>() {
        }.getType();

        return modelMapper.map(entities, listType);
    }
}
