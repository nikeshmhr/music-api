package com.nikesh.musicplaylistapi.exception;

/**
 * @author Nikesh Maharjan
 *         nikeshmhr@gmail.com
 */
public class OperationUnsuccessfulException extends RuntimeException {
    public OperationUnsuccessfulException() {
        super();
    }

    public OperationUnsuccessfulException(String message) {
        super(message);
    }
}
