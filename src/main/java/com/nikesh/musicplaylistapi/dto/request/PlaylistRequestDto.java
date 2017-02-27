package com.nikesh.musicplaylistapi.dto.request;

import com.nikesh.musicplaylistapi.core.ModelBase;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Nikesh Maharjan
 *         nikeshmhr@gmail.com
 */
@Getter
@Setter
public class PlaylistRequestDto extends ModelBase {

    private String playlistName;

}
