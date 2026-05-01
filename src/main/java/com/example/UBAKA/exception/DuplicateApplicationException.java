package com.example.UBAKA.exception;

/**
 * Exception thrown when a duplicate operation is attempted,
 * such as applying to the same job twice.
 */
public class DuplicateApplicationException extends RuntimeException {

    public DuplicateApplicationException(String message) {
        super(message);
    }

    public DuplicateApplicationException(Long jobId, Long engineerId) {
        super("Engineer " + engineerId + " has already applied to Job " + jobId);
    }
}
