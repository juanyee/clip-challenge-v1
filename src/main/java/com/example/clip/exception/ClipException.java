package com.example.clip.exception;

/**
 * @author juan.yee
 */
public class ClipException extends RuntimeException {
    public ClipException() {
    }

    public ClipException(String message) {
        super(message);
    }

    public ClipException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClipException(Throwable cause) {
        super(cause);
    }
}
