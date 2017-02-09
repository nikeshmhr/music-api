package com.nikesh.musicplaylistapi.exception;

/**
 * @author Nikesh Maharjan
 *         nikeshmhr@gmail.com
 */
public class NoRecordFoundException extends RuntimeException {

    public NoRecordFoundException() {
        super();
    }

    public NoRecordFoundException(String message) {
        super(message);
    }

}
