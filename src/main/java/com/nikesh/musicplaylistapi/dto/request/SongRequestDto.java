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
public class SongRequestDto extends ModelBase {

    // TODO add hibernate validation here...

    private String title;

    private String artist;

    private String albumName;

    private String songDuration;

    private String thumbnailImage;

}
