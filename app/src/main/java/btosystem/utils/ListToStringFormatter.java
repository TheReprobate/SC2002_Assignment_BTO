package btosystem.utils;

import java.util.Arrays;
import java.util.List;

public class ListToStringFormatter {
    public static String toString(List<?> data) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        for(int i = 0; i < data.size(); i++) {
            sb.append(String.format("[%d] %s\n", i+1, data.get(i)));
        }
        return sb.toString();
    }
    public static String toString(Object[] data) {
        return toString(Arrays.asList(data));
    }
}
