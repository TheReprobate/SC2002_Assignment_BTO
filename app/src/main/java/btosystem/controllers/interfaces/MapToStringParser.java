package btosystem.controllers.interfaces;

import java.util.Map;

public interface MapToStringParser<T> {
    String toString(Map<?, T> data);
}
