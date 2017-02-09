package com.nikesh.musicplaylistapi.dto.response;

import com.nikesh.musicplaylistapi.core.ModelBase;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Nikesh Maharjan.
 *         nikeshmhr@gmail.com
 */
@Getter
@Setter
public class UserResponseDTO extends ModelBase {

    private long id;

    private String username;
}
