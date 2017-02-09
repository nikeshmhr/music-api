package com.nikesh.musicplaylistapi.dto.request;

import com.nikesh.musicplaylistapi.core.ModelBase;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * @author Nikesh Maharjan
 *         nikeshmhr@gmail.com
 */
@Getter
@Setter
public class UserRequestDTO extends ModelBase {

    private long id;

    @Length(min = 2, max = 15, message = "Username must be {min} to {max} characters long.")
    private String username;

    @Length(min = 8, max = 20, message = "Password must be {min} to {max} characters long.")
    private String password;

}
