package btosystem.controllers.interfaces;

import java.util.List;

public interface ListToString<T> extends ToString<T> {
    String toString(List<T> data);
}
