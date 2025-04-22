package btosystem.controllers.interfaces;

/**
 * An interface for cleanup of resources when deleted.
 */
public interface CleanupOperations<T> {
    /**
     * Method to be implemented.
     */
    void cleanup(T instance);
}
