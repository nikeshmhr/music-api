package com.nikesh.musicplaylistapi.exception;

/**
 * @author Nikesh Maharjan
 *         nikeshmhr@gmail.com
 */
public class DuplicateDataException extends RuntimeException {

    public DuplicateDataException() {
        super();
    }

    public DuplicateDataException(String message) {
        super(message);
    }
}
