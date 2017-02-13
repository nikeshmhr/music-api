package com.nikesh.musicplaylistapi.factory;

import com.nikesh.musicplaylistapi.dto.response.error.ErrorResponse;
import org.springframework.http.HttpStatus;

/**
 * @author Nikesh Maharjan
 *         nikeshmhr@gmail.com
 */
public class ErrorFactory {

    public static ErrorResponse getErrorResponse(Throwable throwable, HttpStatus httpStatus) {
        return new ErrorResponse(throwable.getMessage(), httpStatus.value(), httpStatus);

        // TODO cleanup this mess
//        switch (httpStatus) {
//            case NOT_FOUND:
//                return new ErrorResponse(throwable.getMessage(), httpStatus.value(), httpStatus);
//            case BAD_REQUEST:
//                return new ErrorResponse(throwable.getMessage(), httpStatus.value(), httpStatus);
//            case CONFLICT:
//                return new ErrorResponse(throwable.getMessage(), DuplicateDataException.CONFLICT_STATUS.value(), DuplicateDataException.CONFLICT_STATUS);
//            default:
//                return null;
//        }
    }

}
