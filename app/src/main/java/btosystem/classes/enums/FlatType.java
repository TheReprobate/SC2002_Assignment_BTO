package btosystem.classes.enums;

public enum FlatType {
    TWO_ROOM,
    THREE_ROOM;

    @Override
    public String toString() {
        return switch (this) {
            case TWO_ROOM -> "Two-Room";
            case THREE_ROOM -> "Three-Room";
        };
    }
}
