package btosystem.controllers.interfaces;

import java.util.List;

public interface ListToStringSerializer<T> extends ToStringSerializer<T> {
    String toString(List<T> data);
}
