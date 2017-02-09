package com.nikesh.musicplaylistapi.factory;

import com.nikesh.musicplaylistapi.dto.response.error.ErrorResponse;
import org.springframework.http.HttpStatus;

/**
 * @author Nikesh Maharjan
 *         nikeshmhr@gmail.com
 */
public class ErrorFactory {

    public static ErrorResponse getErrorResponse(Throwable throwable, HttpStatus httpStatus) {

        switch (httpStatus) {
            case NOT_FOUND:
                return new ErrorResponse(throwable.getMessage(), httpStatus.value(), httpStatus);
            default:
                return null;
        }
    }

}
