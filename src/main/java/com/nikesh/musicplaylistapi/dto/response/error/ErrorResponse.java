package com.nikesh.musicplaylistapi.dto.response.error;

import com.nikesh.musicplaylistapi.core.ModelBase;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * @author Nikesh Maharjan
 *         nikeshmhr@gmail.com
 */
@Getter
@Setter
public class ErrorResponse extends ModelBase {

    private String message;

    private int code;

    private HttpStatus status;

    public ErrorResponse() {
        message = "";
        code = 0;
        status = null;
    }

    public ErrorResponse(String message, int code, HttpStatus status) {
        this.message = message;
        this.code = code;
        this.status = status;
    }

}
