package btosystem.operations.interfaces;

/**
 * Interface for custom ToString function.
 */
public interface ToString<T> {

    /**
     * toString method to implement.
     *
     * @param data to convert to string
     */
    String toString(T data);
}
