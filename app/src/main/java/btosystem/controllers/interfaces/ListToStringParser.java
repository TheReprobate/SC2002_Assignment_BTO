package btosystem.controllers.interfaces;

import java.util.List;

public interface ListToStringParser<T> extends ToStringParser<T> {
    String toString(List<T> data);
}
