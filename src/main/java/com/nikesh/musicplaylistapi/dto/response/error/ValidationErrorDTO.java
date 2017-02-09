package com.nikesh.musicplaylistapi.dto.response.error;

import com.nikesh.musicplaylistapi.core.ModelBase;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Nikesh Maharjan
 *         nikeshmhr@gmail.com
 */
@Getter
@Setter
public class ValidationErrorDTO extends ModelBase {

    private String field;

    private String message;

    public void setFieldError(String field, String message) {
        this.field = field;
        this.message = message;
    }

}
