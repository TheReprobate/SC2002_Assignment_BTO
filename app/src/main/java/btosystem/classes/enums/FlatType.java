package btosystem.classes.enums;

/**
 * Represents the different types of flats available in housing projects.
 */
public enum FlatType {
    TWO_ROOM,
    THREE_ROOM;

    /**
     * Returns a human-readable formatted string representation of the flat type.
     *
     * @return the formatted string representation (e.g., "Two-Room")
     */
    @Override
    public String toString() {
        return switch (this) {
          case TWO_ROOM -> "Two-Room";
          case THREE_ROOM -> "Three-Room";
        };
    }
}
