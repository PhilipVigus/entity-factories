package com.philvigus.exceptions;

public class EntityFactoryException extends RuntimeException {
    public EntityFactoryException(final String message, final Throwable err) {
        super(message, err);
    }
}
