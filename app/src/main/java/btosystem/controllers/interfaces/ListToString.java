package btosystem.controllers.interfaces;

import java.util.List;

/**
 * An interface that extends {@link ToString} to provide string representation
 * functionality for lists of objects.
 *
 * @param <T> the type of objects in the list to be converted to string
 *
 * @see ToString
 */
public interface ListToString<T> extends ToString<T> {
    /**
     * Converts a list of objects into their string representation.
     *
     * @param data the list of objects to convert to string
     * @return string representation of the list contents
     */
    String toString(List<T> data);
}
