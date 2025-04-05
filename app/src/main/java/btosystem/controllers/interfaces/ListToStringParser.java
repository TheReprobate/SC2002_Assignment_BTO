package btosystem.controllers.interfaces;

import java.util.List;

public interface ListToStringParser<T> {
    String toString(List<T> data);
}
