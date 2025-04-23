package btosystem.utils;

import java.util.Arrays;
import java.util.List;

/**
 * A utility class for formatting lists and arrays into numbered string representations.
 * Provides methods to convert collections into human-readable strings with numbered indexes.
 */
public class ListToStringFormatter {

    /**
     * Converts a List of objects into a formatted string with numbered entries.
     * Each element is displayed on a new line preceded by its 1-based index in brackets.
     *
     * @param data the List of objects to format (can contain any type of objects)
     * @return a formatted string with each element numbered on separate lines,
     *         or empty string if the input is null
     * @see List
     */
    public static String toString(List<?> data) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        for (int i = 0; i < data.size(); i++) {
            sb.append(String.format("[%d] %s\n", i + 1, data.get(i)));
        }
        return sb.toString();
    }

    /**
     * Converts an array of objects into a formatted string with numbered entries.
     * Delegates to the List version after converting the array to a List.
     *
     * @param data the array of objects to format (can contain any type of objects)
     * @return a formatted string with each element numbered on separate lines,
     *         or empty string if the input is null
     * @see #toString(List)
     */
    public static String toString(Object[] data) {
        return toString(Arrays.asList(data));
    }
}
