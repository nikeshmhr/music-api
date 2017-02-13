package com.nikesh.musicplaylistapi.exception;

/**
 * @author Nikesh Maharjan
 *         nikeshmhr@gmail.com
 */
public class BadDataException extends RuntimeException {

    public BadDataException() {
        super();
    }

    public BadDataException(String message) {
        super(message);
    }

}
