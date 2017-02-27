package com.nikesh.musicplaylistapi.dto.response;

import com.nikesh.musicplaylistapi.core.ModelBase;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Nikesh Maharjan
 *         nikeshmhr@gmail.com
 */
@Getter
@Setter
public class PlaylistResponseDto extends ModelBase {

    private Long id;

    private String playlistName;

    private Integer numberOfSongs;

}
