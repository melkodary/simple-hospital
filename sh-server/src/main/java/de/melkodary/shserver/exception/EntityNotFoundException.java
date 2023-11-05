package de.melkodary.shserver.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(Class<?> clazz, Long id) {
        super(clazz.getSimpleName() + " #" + id + " not found!");
    }
}
