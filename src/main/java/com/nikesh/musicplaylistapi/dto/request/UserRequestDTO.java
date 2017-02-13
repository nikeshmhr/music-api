package com.nikesh.musicplaylistapi.dto.request;

import com.nikesh.musicplaylistapi.core.ModelBase;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @author Nikesh Maharjan
 *         nikeshmhr@gmail.com
 */
@Getter
@Setter
public class UserRequestDTO extends ModelBase {

    private long id;

    @Length(min = 2, max = 25, message = "Username must be {min} to {max} characters long.")
    @NotNull
    private String username;

    @Length(min = 8, max = 20, message = "Password must be {min} to {max} characters long.")
    @NotNull
    private String password;

}
