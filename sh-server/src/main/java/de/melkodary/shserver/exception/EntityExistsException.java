package de.melkodary.shserver.exception;

public class EntityExistsException extends RuntimeException {
    public EntityExistsException(Class<?> clazz) {
        super(clazz.getSimpleName() + " already exists!");
    }
}

